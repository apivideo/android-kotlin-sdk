package video.api.sdk.android.kotlin.model

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.json.JSONObject
import java.util.*

class Chapter(val body: JSONObject) {

    val uri: String
        get() = body.getString("uri")

    val src: HttpUrl
        get() = body.getString("src").toHttpUrl()

    val language: Locale
        get() = Locale(body.getString("language"))

}