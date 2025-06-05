package ru.alfabank.task1.repository

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.alfabank.task1.mock.BackendSimulator
import java.util.UUID

class Repository(
    private val context: Context
) {

    val prefs = context.getSharedPreferences(UUID.randomUUID().toString(), Context.MODE_PRIVATE)
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(BackendSimulator)
        .build()

    suspend fun getProfileInfo(id: String, token: String): ProfileInfo = withContext(Dispatchers.Default) {
        val profileRequest = Request.Builder()
            .url("https://test.example.ru/getProfile/$id")
            .get()
            .header("X-TOKEN", token)
            .build()

        val profileResponse = okHttpClient.newCall(profileRequest).execute()
        val profile = Json.decodeFromString<Profile>(profileResponse.body!!.string())

        val jobInfoRequest = Request.Builder()
            .url("https://test.example.ru/jobInfo/$id")
            .get()
            .header("X-TOKEN", token)
            .build()

        val jobInfoResponse = okHttpClient.newCall(jobInfoRequest).execute()
        val jobInfo = Json.decodeFromString<JobInfo>(jobInfoResponse.body!!.string())

        ProfileInfo(profile, jobInfo)
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