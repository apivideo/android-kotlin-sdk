package video.api.sdk.android.kotlin.model

import org.json.JSONObject

fun interface BodyTransformer<T> {
    fun fromJson(body: JSONObject): T
}
