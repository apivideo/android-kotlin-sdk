package video.api.androidkotlinsdk

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class UserAgentInterceptor(private var userAgent: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain) =
        chain.proceed(chain.request()
            .newBuilder()
            .header("User-Agent", userAgent)
            .build()
        )

}