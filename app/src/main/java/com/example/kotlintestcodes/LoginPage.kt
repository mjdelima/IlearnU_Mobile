package com.cardinalplayground.ilufaculty

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.cardinalplayground.ilufaculty.R
import com.cardinalplayground.ilufaculty.databinding.ActivityLoginPageBinding
import com.cardinalplayground.ilufaculty.databinding.ActivityMainBinding

class LoginPage : AppCompatActivity() {

    private lateinit var binding: ActivityLoginPageBinding

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login_page)

        var fade : Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        var slidein : Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in)

        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        binding.cardView.startAnimation(slidein)

        binding.btnBack.startAnimation(fade)
        binding.tvWelcome.startAnimation(fade)

    }
}