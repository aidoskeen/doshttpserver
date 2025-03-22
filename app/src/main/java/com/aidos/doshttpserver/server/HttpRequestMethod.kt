package com.aidos.doshttpserver.server

import android.util.Log

enum class HttpRequestMethod {
    GET,
    PUT,
    DELETE,
    POST;

    companion object {
        fun fromString(string: String): HttpRequestMethod? {
            return when (string) {
                GET.name -> GET
                PUT.name -> PUT
                DELETE.name -> DELETE
                POST.name -> POST
                else -> {
                    Log.e("HttpRequestMethod", "Invalid string for HttpRequestMethod")
                    null
                }
            }
        }
    }
}