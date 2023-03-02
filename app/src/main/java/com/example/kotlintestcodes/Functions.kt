package com.example.kotlintestcodes

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun getTime(): String {
//    val date = Calendar.getInstance().time
//    val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
//    return formatter.format(date)

    val datetime1 = LocalDateTime.now()
    val format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
    return datetime1.format(format)

}

fun getTimeDifference(previousTime : String): Long {
    val format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

    val then = LocalDateTime.parse(previousTime, format)
    val now = LocalDateTime.now()

    return then.until(now, ChronoUnit.SECONDS)
}