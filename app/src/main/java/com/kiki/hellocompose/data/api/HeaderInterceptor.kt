package com.kiki.hellocompose.data.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    private val API_KEY = "1epHEkn5zzpFjSAHVbCpOBdXjh2VG2DV"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalUrl = chain.request().url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("key", API_KEY)
            .build()

        val request = chain.request().newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }
}