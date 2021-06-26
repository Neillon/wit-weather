package br.com.neillon.network.interceptor

import br.com.neillon.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(var key: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(key, BuildConfig.WEATHER_API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}

