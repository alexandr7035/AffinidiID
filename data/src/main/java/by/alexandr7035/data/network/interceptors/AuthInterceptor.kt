package by.alexandr7035.data.network.interceptors

import by.alexandr7035.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val newRequest: Request = chain.request().newBuilder()
            .header("Api-Key", BuildConfig.API_KEY)
            .method(request.method, request.body)
            .build()

        return chain.proceed(newRequest)
    }
}