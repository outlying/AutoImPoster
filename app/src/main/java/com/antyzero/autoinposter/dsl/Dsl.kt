package com.antyzero.autoinposter.dsl

import android.content.Context
import android.widget.Toast
import com.antyzero.autoinposter.AutoInPosterApplication


fun Context.showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

@Suppress("PropertyName")
inline val Any.TAG: String
    get() = this.javaClass.simpleName

inline val Context.applicationComponent
    get() = (this.applicationContext as AutoInPosterApplication).applicationComponent