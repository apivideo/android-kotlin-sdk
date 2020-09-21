package video.api.sdk.android.kotlin.model

import okhttp3.HttpUrl
import org.json.JSONObject

class Link(
    val body: JSONObject
) {
    val rel: String?
        get() = body.getStringOrNull("rel")

    val uri: HttpUrl?
        get() = body.getHttpUrlOrNull("uri")
}