package by.alexandr7035.data.datasource.cloud

import retrofit2.Response

interface ApiCallHelper {
    suspend fun <T> executeCall(apiCall: suspend() -> Response<T>): ApiCallWrapper<T>
}