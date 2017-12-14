package com.antyzero.autoinposter.network

import io.reactivex.Flowable
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class OkHttpInPostCalls(private val okHttpClient: OkHttpClient) : InPostCalls {

    override fun keepOriginalDestination(id: String): Flowable<Response> {
        return Flowable.fromCallable {
            okHttpClient.newCall(Request.Builder().url(getNewUrl(id, Action.ADD)).build()).execute()
        }
    }

    companion object {

        private fun getNewUrl(id: String, action: Action? = null) = BASE_URL.newBuilder()
                .addQueryParameter("t", id).apply {

            if (action != null) {
                addQueryParameter("action", action.toString())
            }
        }.build()

        private val BASE_URL: HttpUrl = HttpUrl.parse("https://kurier.inpost.pl/rp.aspx")!!
    }

    private enum class Action {

        ADD;

        override fun toString() = name.toLowerCase()
    }
}