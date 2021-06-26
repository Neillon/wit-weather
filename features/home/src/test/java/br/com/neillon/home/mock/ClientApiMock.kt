package br.com.neillon.home.mock

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientApiMock(private val baseUrl: String) {
    private var retrofit: Retrofit? = null
    private val clientMock: Retrofit
        get() {
            if (retrofit == null) {
                val httpClient = OkHttpClient.Builder()

                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .client(httpClient.build())
                    .build()
            }

            return retrofit as Retrofit
        }

    fun <S> createService(serviceClass: Class<S>): S {
        return clientMock.create(serviceClass)
    }
}