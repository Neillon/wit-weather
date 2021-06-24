package br.com.neillon.network.extensions

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpExtensions {
    fun OkHttpClient.Builder.addLogInterceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)

        return this
    }
}