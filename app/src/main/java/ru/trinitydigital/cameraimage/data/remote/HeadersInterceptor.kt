package ru.trinitydigital.cameraimage.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import ru.trinitydigital.cameraimage.data.local.PrefsHelper

class HeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = PrefsHelper.getToken()
        val request = chain.request().newBuilder()
        if (token.isNotEmpty())
            request.addHeader("token", token)

        return chain.proceed(request.build())
    }
}