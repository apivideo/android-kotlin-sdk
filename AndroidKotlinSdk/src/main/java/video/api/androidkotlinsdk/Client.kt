package video.api.androidkotlinsdk

import okhttp3.OkHttpClient
import video.api.androidkotlinsdk.api.*
import video.api.androidkotlinsdk.http.HttpRequestExecutor

class Client(
    val videos: VideoApi,
    val players: PlayerApi,
    val captions: CaptionApi,
    val chapters: ChapterApi,
    val liveStreams: LiveStreamApi
) {

    companion object {
        var productionBaseUri = "https://ws.api.video"
        var sandboxBaseUri = "https://sandbox.api.video"
        var userAgent = "api.video SDK (android; v:0.1.6; )"

        fun createProduction(key: String): Client {
            return createClient(TokenHolder(), productionBaseUri, key)
        }

        fun createSandbox(key: String): Client {
            return createClient(TokenHolder(), sandboxBaseUri, key)
        }

        private fun createClient(
            tokenHolder: TokenHolder,
            baseUri: String,
            key: String
        ): Client {
            val client = OkHttpClient().newBuilder()
                .authenticator(TokenAuthenticator(tokenHolder, baseUri, key))
                .addInterceptor(UserAgentInterceptor(userAgent))
                .addInterceptor(Authorizer(tokenHolder))
                .build()

            val executor = HttpRequestExecutor(client)

            return Client(
                VideoApi(baseUri, executor, client),
                PlayerApi(baseUri, executor),
                CaptionApi(baseUri, executor),
                ChapterApi(baseUri, executor),
                LiveStreamApi(baseUri, executor)
            )
        }
    }

}