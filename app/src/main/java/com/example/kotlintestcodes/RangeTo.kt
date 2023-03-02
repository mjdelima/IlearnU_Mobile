package com.example.kotlintestcodes

fun main(args: Array<String>) {
    //var myCharRange = "a".."z"
    var myCharRange = 'a'.rangeTo('z')
    var mychar = 'k' in myCharRange

    println("Mychar has k: $mychar")

}