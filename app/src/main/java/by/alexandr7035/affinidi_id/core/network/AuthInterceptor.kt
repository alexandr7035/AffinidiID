package by.alexandr7035.affinidi_id.core.network

import by.alexandr7035.affinidi_id.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        return chain.proceed(chain.request().withHeader("Authorization", "token ${appPreferences.token}"))

        val request = chain.request()

        val newRequest: Request = chain.request().newBuilder()
            .header("Api-Key", BuildConfig.API_KEY)
            .method(request.method, request.body)
            .build()

        return chain.proceed(newRequest)
    }
}