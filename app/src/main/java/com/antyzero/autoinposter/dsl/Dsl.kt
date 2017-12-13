package com.antyzero.autoinposter.dsl

import android.content.Context
import android.widget.Toast
import com.antyzero.autoinposter.AutoInPosterApplication


fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun <T> T?.toList() = listOf(this)

inline val Context.applicationComponent
    get() = (this.applicationContext as AutoInPosterApplication).applicationComponent