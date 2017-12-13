package com.antyzero.autoinposter.network

import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.Response


interface InPostCalls {

    fun keepOriginalDestination(id: String): Flowable<Response>
}