package ru.alfabank.task1.mock

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import ru.alfabank.task1.repository.JobInfo
import ru.alfabank.task1.repository.LoginData
import ru.alfabank.task1.repository.LoginResult
import ru.alfabank.task1.repository.Profile

// Файл можно не смотреть, не является частью интервью, эмуляция бекенда
object BackendSimulator : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Thread.sleep(200)
        val paths = chain.request().url.encodedPathSegments

        return when {
            paths.contains("getProfile") -> createProfileResponse(chain.request())
            paths.contains("login") -> createLoginResult(chain.request())
            paths.contains("jobInfo") -> createJobInfoResult(chain.request())
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

    private fun createJobInfoResult(request: Request): Response {
        val profile = JobInfo(
            organizationName = "Alfa-Bank"
        )

        return profile.toResponse(request)
    }

    private fun createLoginResult(request: Request): Response {
        val stringBody = request.body.let { body ->
            val buffer = okio.Buffer()
            body?.writeTo(buffer)
            buffer.readUtf8()
        }
        val login = Json.decodeFromString<LoginData>(stringBody)
        if (login.login != "test" && login.password != "test") {
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_2)
                .code(403)
                .message("")
                .build()
        }

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