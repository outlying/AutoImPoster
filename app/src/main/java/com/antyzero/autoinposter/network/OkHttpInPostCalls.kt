package com.antyzero.autoinposter.network

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import okhttp3.*
import java.io.IOException


class OkHttpInPostCalls(private val okHttpClient: OkHttpClient) : InPostCalls {

    override fun keepOriginalDestination(id: String): Flowable<Response> {

        /*
        return Flowable.defer {
            try {
                val httpUrl = getNewUrl(id, Action.ADD)
                val request = Request.Builder().url(httpUrl).build()
                val response = okHttpClient.newCall(request).execute()

                return@defer Flowable.just(response)
            } catch (e: IOException) {
                return@defer Flowable.error<Response>(Error(""))
            }
        }
        */

        return Flowable.create<Response>({

            val httpUrl = getNewUrl(id, Action.ADD)
            val request = Request.Builder().url(httpUrl).build()

            okHttpClient.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call?, e: IOException) {
                    it.onError(e)
                }

                override fun onResponse(call: Call?, response: Response) {
                    if (response.isSuccessful) {
                        it.onNext(response)
                        it.onComplete()
                    } else if (!response.isSuccessful) {
                        it.onError(Exception("Fail"))
                    }
                }
            })
        }, BackpressureStrategy.ERROR)


        /*
        return Observable.fromCallable {
            okHttpClient.newCall(Request.Builder().url(getNewUrl(id, Action.ADD)).build()).execute()
        }
        */
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