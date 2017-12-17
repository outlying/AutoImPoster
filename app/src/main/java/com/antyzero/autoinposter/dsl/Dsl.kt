package com.antyzero.autoinposter.dsl

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.widget.Toast
import com.antyzero.autoinposter.AutoInPosterApplication
import com.antyzero.autoinposter.domain.TAG


fun Context.showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun DialogFragment.show(manager: FragmentManager) {
    this.show(manager, TAG)
}

inline val Context.applicationComponent
    get() = (this.applicationContext as AutoInPosterApplication).applicationComponent