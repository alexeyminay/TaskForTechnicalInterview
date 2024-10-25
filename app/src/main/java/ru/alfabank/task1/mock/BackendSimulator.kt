package ru.alfabank.task1.mock

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import ru.alfabank.task1.repository.LoginResult
import ru.alfabank.task1.repository.Profile

object BackendSimulator : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val paths = chain.request().url.encodedPathSegments

        return when {
            paths.contains("getProfile") -> createProfileResponse(chain.request())
            paths.contains("login") -> createLoginResult(chain.request())
            else -> throw IllegalStateException()
        }
    }

    private fun createProfileResponse(request: Request): Response {
        val profile = Profile(
            firstName = "Ivan",
            lastName = "Ivanov",
        )

        return profile.toResponse(request)
    }

    private fun createLoginResult(request: Request): Response {
        val loginResult = LoginResult(
            token = "token",
            userId = "userId"
        )

        return loginResult.toResponse(request)
    }

    private inline fun <reified T> T.toResponse(request: Request): Response {
        val body = Json.encodeToString(serializer(), this).toResponseBody()
        return Response.Builder()
            .body(body)
            .request(request)
            .protocol(Protocol.HTTP_2)
            .code(200)
            .message("")
            .build()
    }

}