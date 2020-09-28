package video.api.androidkotlinsdk

import okhttp3.Interceptor

class Authorizer(
    private val tokenHolder: TokenHolder
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain) =
        chain.proceed(chain.request().addAuthorization(tokenHolder.token))

}