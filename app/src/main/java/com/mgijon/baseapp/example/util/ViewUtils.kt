package com.mgijon.baseapp.example.util

import android.view.View

fun View.gone(){
    this.visibility = View.GONE
}
fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.animateTranslationY(duration : Int, destiny : Int = 0){
    this.animate().translationY(destiny.toFloat()).duration = duration.toLong()
}

fun View.animateTranslationX(duration : Int, destiny : Int = 0){
    this.animate().translationX(destiny.toFloat()).duration = duration.toLong()
}