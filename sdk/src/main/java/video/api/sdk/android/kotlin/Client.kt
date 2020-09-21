package video.api.sdk.android.kotlin

import okhttp3.OkHttpClient
import video.api.sdk.android.kotlin.api.*
import video.api.sdk.android.kotlin.http.HttpRequestExecutor

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
                .addInterceptor(Authorizer(tokenHolder))
                .build()

            val executor = HttpRequestExecutor(client)

            return Client(
                VideoApi(baseUri, executor),
                PlayerApi(baseUri, executor),
                CaptionApi(baseUri, executor),
                ChapterApi(baseUri, executor),
                LiveStreamApi(baseUri, executor)
            )
        }
    }

}