package az.khayalsharifli.bankrespublika.data.error

import az.khayalsharifli.bankrespublika.domain.error.ErrorMapper
import az.khayalsharifli.bankrespublika.domain.error.NetworkError
import az.khayalsharifli.bankrespublika.domain.error.ServerError
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RemoteErrorMapper : ErrorMapper {
    override fun mapError(throwable: Throwable) = when (throwable) {
        is HttpException -> mapHttpErrors(throwable)
        is SocketException,
        is SocketTimeoutException,
        is UnknownHostException -> NetworkError(throwable)
        else -> az.khayalsharifli.bankrespublika.domain.error.UnknownError(throwable)
    }

    private fun mapHttpErrors(exception: HttpException): Throwable {
        val description = try {
            exception
                .response()
                ?.errorBody()
                ?.string()
                ?.let { Json.decodeFromString<ServerProblemDescription>(it) }
        } catch (e: Throwable) {
            null
        } ?: ServerProblemDescription()

        return when (exception.code()) {
            401 -> ServerError.NotAuthorized(description.code, description.message)
            in 500..600 -> {
                ServerError.ServerIsDown(description.code, description.message)
            }
            else -> {
                ServerError.Unexpected(description.code, description.message)
            }
        }
    }
}