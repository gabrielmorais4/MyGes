package com.example.myges.core.data.repository

import android.util.Base64
import com.example.myges.core.domain.repository.AuthRepository
import com.example.myges.util.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLDecoder
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager
) : AuthRepository {

    private val authClient = OkHttpClient.Builder()
        .followRedirects(false)
        .followSslRedirects(false)
        .build()

    override suspend fun login(username: String, password: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val credentials = Base64.encodeToString(
                    "$username:$password".toByteArray(Charsets.UTF_8),
                    Base64.NO_WRAP
                )

                val request = Request.Builder()
                    .url(AUTH_URL)
                    .header("Authorization", "Basic $credentials")
                    .get()
                    .build()

                val response = authClient.newCall(request).execute()

                val location = response.header("Location")
                    ?: error("No Location header in auth response (HTTP ${response.code})")

                val rawToken = Regex("access_token=([^&]+)")
                    .find(location)
                    ?.groupValues
                    ?.get(1)
                    ?: error("access_token not found in redirect URL")

                val token = URLDecoder.decode(rawToken, "UTF-8")
                tokenManager.saveToken("Bearer $token")
            }
        }

    override fun isLoggedIn(): Boolean = tokenManager.isLoggedIn()

    override fun logout() = tokenManager.clearToken()

    companion object {
        private const val AUTH_URL =
            "https://authentication.kordis.fr/oauth/authorize" +
                    "?response_type=token&client_id=skolae-app"
    }
}
