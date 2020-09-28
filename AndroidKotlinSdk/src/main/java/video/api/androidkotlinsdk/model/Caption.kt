package video.api.androidkotlinsdk.model

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.json.JSONObject
import java.util.*

class Caption(val body: JSONObject) {

    val default: Boolean
        get() = body.getBoolean("default")

    val uri: HttpUrl
        get() = body.getString("uri").toHttpUrl()

    val src: HttpUrl
        get() = body.getString("src").toHttpUrl()

    val srcLang: Locale
        get() = Locale(body.getString("srclang"))

}