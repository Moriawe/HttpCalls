package com.moriawe.ktorapp.data.remote

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException

sealed class Resource<T> {
    data class Success<T>(val data: T? = null) : Resource<T>()
    data class Error<T>(val error: String) : Resource<T>()
}

enum class NetworkError {
    SERVICE_UNAVAILABLE,
    REDIRECT_ERROR,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class NetworkException(private val error: NetworkError) : Exception(
    "An error occurred when translating: $error"
)

class BaseRepository {

    // Handle API call and only returning data if it was a success
    suspend fun <T> handleApiCall(call: suspend () -> T): Resource<T> {
        return try {
            val result = call()
            Resource.Success(result)
        } catch (e: IOException) {
            // No service
            throw NetworkException(NetworkError.SERVICE_UNAVAILABLE)
        } catch (e: RedirectResponseException) {
            // 3xx responses
            throw NetworkException(NetworkError.REDIRECT_ERROR)
        } catch (e: ClientRequestException) {
            // 4xx responses
            throw NetworkException(NetworkError.CLIENT_ERROR)
        } catch (e: ServerResponseException) {
            // 5xx responses
            throw NetworkException(NetworkError.SERVER_ERROR)
        } catch (e: Exception) {
            // General exception
            throw NetworkException(NetworkError.UNKNOWN_ERROR)
        }
    }

    // Handle API call and return the Success or Error with error message
    suspend fun <T> handleApiCall2(call: suspend () -> T): Resource<T> {
        return try {
            val result = call()
            Resource.Success(result)
        } catch (e: IOException) {
            // No service
            Resource.Error(NetworkError.SERVICE_UNAVAILABLE.name)
        } catch (e: RedirectResponseException) {
            // 3xx responses
            Resource.Error(NetworkError.REDIRECT_ERROR.name)
        } catch (e: ClientRequestException) {
            // 4xx responses
            Resource.Error(NetworkError.CLIENT_ERROR.name)
        } catch (e: ServerResponseException) {
            // 5xx responses
            Resource.Error(NetworkError.SERVER_ERROR.name)
        } catch (e: Exception) {
            // General exception
            Resource.Error(NetworkError.UNKNOWN_ERROR.name)
        }
    }

    // Chat-GTPs suggestion
    suspend fun <T> handleApiCall3(call: suspend () -> T): Resource<T> {
        return try {
            val result = call()
            Resource.Success(result)
        } catch (e: ClientRequestException) {
            val error = when (e.response.status) {
                HttpStatusCode.NotFound,
                HttpStatusCode.BadRequest,
                HttpStatusCode.Unauthorized,
                HttpStatusCode.Forbidden,
                HttpStatusCode.MethodNotAllowed -> NetworkError.CLIENT_ERROR

                else -> NetworkError.UNKNOWN_ERROR
            }
            Resource.Error(error.name)
        } catch (e: ServerResponseException) {
            val error = when (e.response.status) {
                HttpStatusCode.InternalServerError -> NetworkError.SERVER_ERROR
                else -> NetworkError.UNKNOWN_ERROR
            }
            Resource.Error(error.name)
        } catch (e: Exception) {
            Resource.Error(NetworkError.UNKNOWN_ERROR.name)
        }
    }
}
