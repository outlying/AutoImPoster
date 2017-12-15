package com.antyzero.autoinposter.domain.network

import io.reactivex.Flowable
import okhttp3.Response


interface InPostCalls {

    fun keepOriginalDestination(id: String): Flowable<Response>
}