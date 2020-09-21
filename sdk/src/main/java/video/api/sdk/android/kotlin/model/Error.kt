package video.api.sdk.android.kotlin.model

import org.json.JSONObject

class Error(val body: JSONObject) {

    val name: String?
        get() = body.getStringOrNull("name")

    val type: String?
        get() = body.getStringOrNull("type")

    val title: String?
        get() = body.getStringOrNull("title")


    override fun toString(): String {
        return body.toString()
    }
}