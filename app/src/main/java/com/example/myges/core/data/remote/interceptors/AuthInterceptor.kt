package com.example.myges.core.data.remote.interceptors

import com.example.myges.util.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()

        val request = if (token != null) {
            chain.request().newBuilder()
                .header("Authorization", token)
                .build()
        } else {
            chain.request()
        }

        val response = chain.proceed(request)

        if (response.code == 401) {
            tokenManager.clearToken()
        }

        return response
    }
}
