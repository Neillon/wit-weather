package br.com.neillon.network.manager

import android.util.Log
import br.com.neillon.network.Constants
import br.com.neillon.network.errorhandling.ErrorResponse
import br.com.neillon.network.exception.InvalidApiKeyException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

object NetworkManager {

    private const val TAG = "NetworkManager"

    suspend fun <S> doAsyncRequest(
        block: suspend () -> Response<S>
    ): S {
        return try {
            val response = block()
            response.takeIf { it.isSuccessful }?.body()
                ?: when (response.code()) {
                    Constants.Network.Exceptions.INVALID_API_KEY_STATUS_CODE -> throw InvalidApiKeyException()
                    else -> throw Exception(deserializeError(response)) // Why ChuckNorrisApi returns the error in status code 500???
                }

        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            throw Exception(e.message!!)
        }
    }

    private fun <S> deserializeError(response: Response<S>): String {
        val gson = Gson()
        val type = object : TypeToken<ErrorResponse>() {}.type
        var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)

        return errorResponse?.message?.let {
            it.split(":").last()
        } ?: Constants.Network.Exceptions.Messages.NOT_SPECIFIED_ERROR
    }
}