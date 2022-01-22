package by.alexandr7035.data.datasource.cloud.interceptors

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.extensions.debug
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException

class ErrorInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val res = try {
            chain.proceed(chain.request())
        }
        catch (e: UnknownHostException) {
            e.printStackTrace()
            throw AppError(ErrorType.FAILED_CONNECTION)
        }
        catch (e: ConnectException) {
            e.printStackTrace()
            throw AppError(ErrorType.FAILED_CONNECTION)
        }

        Timber.debug("INTERCEPTOR ${res.code}")
        return res
    }
}