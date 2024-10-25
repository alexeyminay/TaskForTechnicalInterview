package ru.alfabank.task1.repository

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.alfabank.task1.mock.BackendSimulator
import java.util.UUID

class ProfileRepository(
    private val context: Context
) {

    val prefs = context.getSharedPreferences(UUID.randomUUID().toString(), Context.MODE_PRIVATE)
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(BackendSimulator)
        .build()

    suspend fun getProfile(id: String, token: String): Profile = withContext(Dispatchers.Default) {
        val request = Request.Builder()
            .url("https://test.example.ru/getProfile/$id")
            .get()
            .header("X-TOKEN", token)
            .build()
        val response = okHttpClient.newCall(request).execute()
        Json.decodeFromString(response.body!!.string())
    }

    suspend fun login(login: LoginData): LoginResult = withContext(Dispatchers.Default) {
        val body = Json.encodeToString(login)
        val request = Request.Builder()
            .url("https://test.example.ru/login")
            .post(body.toRequestBody())
            .build()
        val response = okHttpClient.newCall(request).execute()
        val loginResult = Json.decodeFromString<LoginResult>(response.body!!.string())
        prefs.edit {
            putString("token", loginResult.token)
            putString("userId", loginResult.userId)
        }
        loginResult
    }

}