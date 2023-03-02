package com.cardinalplayground.ilufaculty

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cardinalplayground.ilufaculty.databinding.ActivityMainBinding
import com.example.kotlintestcodes.TinyDB
import com.example.kotlintestcodes.getTime
import com.example.kotlintestcodes.getTimeDifference
import java.time.LocalDateTime


class MainActivity : AppCompatActivity() {
    // instantiate sharedPrefence
    lateinit var tinyDB : TinyDB

    private var log_list = ArrayList<String>()

    private var oldRunning = false
    private lateinit var binding: ActivityMainBinding
    private val link = "https://ilearnu.lu.edu.ph/faculty"
    private var lastTime : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadWebsite()
        lastTime = getTime()



        tinyDB = TinyDB(applicationContext)
    }

    //loadWebsite
    private fun loadWebsite(){
        binding.webView.apply {
            webViewClient = WebViewClient()

            loadUrl(link)
            settings.apply {
                javaScriptEnabled=true
                cacheMode = WebSettings.LOAD_DEFAULT
                setSupportZoom(false)
                builtInZoomControls = false
                displayZoomControls = false
                textZoom = 100
                blockNetworkImage = false
                loadsImagesAutomatically = true
                domStorageEnabled = true
            }

            //DOWNLOAD LISTENER
            setDownloadListener(DownloadListener { url: String?, userAgent: String?, contentDisposition: String?, mimeType: String?, contentLength: Long ->
                val request =DownloadManager.Request(Uri.parse(url))
                request.setMimeType(mimeType)
                val cookies =CookieManager.getInstance().getCookie(url)
                request.addRequestHeader("cookie", cookies)
                request.addRequestHeader("User-Agent", userAgent)
                request.setDescription("Downloading file....")
                request.setTitle(URLUtil.guessFileName(url,contentDisposition,mimeType))
                request.allowScanningByMediaScanner()
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,URLUtil.guessFileName(url, contentDisposition, mimeType))
                val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                dm.enqueue(request)
                Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_SHORT).show()
            })
            //END OF DOWNLOAD MODULE
        }
    }

    override fun onBackPressed() {
        if(binding.webView.canGoBack()){
            binding.webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        val current = LocalDateTime.now()
        
        if(isTopResumedActivity){
            if(oldRunning){
               Log.d("CHECK ACTIVE",  "${getTime()} : FOCUS RECOVER for ${getTimeDifference(lastTime)} second/s!")
                log_list.add(0, "${getTime()} : FOCUS RECOVER for ${getTimeDifference(lastTime)} second/s!")
            }else{
                log_list = tinyDB.getListString("logs")
                log_list.add(0, "=== APPLICATION RESTARTED ====")
                oldRunning = true
//                log_list.add(0, "${getTime()} : FOCUS RECOVER for ${getTimeDifference(lastTime)} second/s!")
            }
        }else{
            Log.d("CHECK ACTIVE", "${getTime()} : FOCUS LOST!")
            log_list.add(0, "${getTime()} : FOCUS LOST!")
            lastTime = getTime()
        }

        tinyDB.putListString("logs", log_list)

        printLog()
    }

    override fun onDestroy() {
        log_list.add(0, "=== EXITED APPLICATION ====")
        tinyDB.putListString("logs", log_list)
        super.onDestroy()
    }



    //CUSTOM FUNCTIONS
    fun printLog(){
        var saved_log : ArrayList<String> = tinyDB.getListString("logs")
        Log.d("LOGS", "============= LOGS ==============")
        for (s in saved_log) {
            Log.d("LOGS", s.toString())
        }

        Log.d("LOGS", "=================================")

    }





}