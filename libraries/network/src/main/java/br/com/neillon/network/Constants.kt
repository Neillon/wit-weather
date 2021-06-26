package br.com.neillon.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object Constants {
    object Network {
        object Exceptions {
            const val INVALID_API_KEY_STATUS_CODE = 401

            const val NOT_SPECIFIED_ERROR_MESSAGE = "Not specified error"
            const val INVALID_API_KEY_STATUS_MESSAGE = "Invalid API key"
        }

        object Limits {
            const val CONNECT = 60L
            const val READ = 60L
            const val WRITE = 60L
        }

        object GsonDefaults {
            // Api returns date in the format 2020-01-05 13:42:19.576875
            const val apiDataFormatBrazil = "yyyy-MM-dd'T'HH:mm:ss:mm"
            val gsonDefault: Gson = GsonBuilder()
                .setDateFormat(apiDataFormatBrazil)
                .create()
        }
    }
}