package br.com.neillon.network.extensions

import br.com.neillon.network.interceptor.AuthenticationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpExtensions {
    fun OkHttpClient.Builder.addLogInterceptor(): OkHttpClient.Builder {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)

        return this
    }

    fun OkHttpClient.Builder.addAuthInterceptor(key: String) {
        addInterceptor(AuthenticationInterceptor(key))
    }
}