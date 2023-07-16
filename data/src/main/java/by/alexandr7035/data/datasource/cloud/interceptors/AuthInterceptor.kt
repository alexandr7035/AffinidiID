package by.alexandr7035.data.datasource.cloud.interceptors

import by.alexandr7035.data.BuildConfig
import by.alexandr7035.data.secrets.Secrets
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor: Interceptor {
    private val apiKey = Secrets().getAffinidiKey("${BuildConfig.LIBRARY_PACKAGE_NAME}.secrets")

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest: Request = chain.request().newBuilder()
            .header("Api-Key", apiKey)
            .method(request.method, request.body)
            .build()

        return chain.proceed(newRequest)
    }
}