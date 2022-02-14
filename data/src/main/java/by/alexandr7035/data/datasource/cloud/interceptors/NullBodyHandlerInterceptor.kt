package by.alexandr7035.data.datasource.cloud.interceptors

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class NullBodyHandlerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val res = chain.proceed(req)

        return if (res.code == 204) {
            val newBody = NO_CONTENT_RESPONSE_JSON.toResponseBody(MEDIA_TYPE.toMediaTypeOrNull())
            res.newBuilder().body(newBody).code(200).build()
        } else {
            res
        }
    }

    companion object {
        private const val MEDIA_TYPE = "application/json"
        private const val NO_CONTENT_RESPONSE_JSON = "{}"
    }
}