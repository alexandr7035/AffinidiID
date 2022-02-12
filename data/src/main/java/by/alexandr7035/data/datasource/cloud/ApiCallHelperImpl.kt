package by.alexandr7035.data.datasource.cloud

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.data.core.AppError
import retrofit2.Response

class ApiCallHelperImpl : ApiCallHelper {
    override suspend fun <T> executeCall(apiCall: suspend() -> Response<T>): ApiCallWrapper {
        try {
            val result = apiCall.invoke()

            return if (result.isSuccessful) {
                val data = result.body()
                ApiCallWrapper.Success(data)
            } else {
                ApiCallWrapper.HttpError(result.code())
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return ApiCallWrapper.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return ApiCallWrapper.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }
}