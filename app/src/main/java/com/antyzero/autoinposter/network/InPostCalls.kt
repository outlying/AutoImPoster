package com.antyzero.autoinposter.network

import io.reactivex.Observable
import okhttp3.Response


interface InPostCalls {

    fun keepOriginalDestination(id: String): Observable<Response>
}