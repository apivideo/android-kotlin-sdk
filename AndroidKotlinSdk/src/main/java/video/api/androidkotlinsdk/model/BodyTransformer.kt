package video.api.androidkotlinsdk.model

import org.json.JSONObject

fun interface BodyTransformer<T> {
    fun fromJson(body: JSONObject): T
}
