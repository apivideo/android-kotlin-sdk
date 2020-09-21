package video.api.sdk.android.kotlin.model

import okhttp3.HttpUrl
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class Video(val body: JSONObject = JSONObject()) {
    constructor(title:String) : this(JSONObject().put("title", title))
    class QueryParams : video.api.sdk.android.kotlin.model.QueryParams() {
        // Filter
        fun description(value: String) = setSingle("description", value) as QueryParams
        fun liveStreamId(value: String) = setSingle("liveStreamId", value) as QueryParams
        fun title(value: String) = setSingle("title", value) as QueryParams
        fun tags(values: Set<String>) = setSet("tags", values) as QueryParams
        fun metadata(values: Map<String, String>) = setMap("metadata", values) as QueryParams

        // Sort
        fun sortByPublishedAt(order: SortOrder = SortOrder.ASC) =
            sortBy("publishedAt", order) as QueryParams

        fun sortByTitle(order: SortOrder = SortOrder.ASC) =
            sortBy("title", order) as QueryParams
    }

    class Source(val body: JSONObject) {
        class LiveStreamSource(val body: JSONObject) {
            val liveStreamId: String
                get() = body.getString("liveStreamId")

            val links: List<Link>
                get() {
                    val links: MutableList<Link> = mutableListOf()

                    body.getJSONArray("links").iterator<JSONObject>().forEach {
                        links.add(Link(it))
                    }

                    return links
                }
        }

        val uri: HttpUrl?
            get() = body.getHttpUrlOrNull("uri")

        val type: String?
            get() = body.getStringOrNull("type")

        val liveStream: LiveStreamSource?
            get() = if (body.has("liveStream")) {
                LiveStreamSource(body.getJSONObject("liveStream"))
            } else {
                null
            }
    }


    // Read

    val videoId: String?
        get() = body.getStringOrNull("videoId")

    val publishedAt: Date?
        get() = body.getDateOrNull("publishedAt")

    val assets: Assets?
        get() = if (body.has("assets")) {
            Assets(body.getJSONObject("assets"))
        } else {
            null
        }

    val sourceObject: Source?
        get() = if (body.has("sourceObject")) {
            Source(body.getJSONObject("sourceObject"))
        } else {
            null
        }


    // Read/write

    var title: String?
        get() = body.getStringOrNull("title")
        set(value) {
            body.put("title", value)
        }

    var source: HttpUrl?
        get() = body.getHttpUrlOrNull("source")
        set(value) {
            body.put("source", value.toString())
        }

    var description: String?
        get() = body.getStringOrNull("description")
        set(value) {
            body.put("description", value)
        }

    var playerId: String?
        get() = body.getStringOrNull("playerId")
        set(value) {
            body.put("playerId", value)
        }

    var public: Boolean
        get() = body.getBooleanOrDefault("public", true)
        set(value) {
            body.put("public", value)
        }

    var tags: Set<String>
        get() {
            val tags = mutableSetOf<String>()

            if (body.has("tags")) {
                body.getJSONArray("tags").iterator<String>().forEach {
                    tags.add(it)
                }
            }

            return tags
        }
        set(value) {
            body.put("tags", JSONArray(value))
        }

    var metadata: Map<String, String>
        get() {
            val metadata = mutableMapOf<String, String>()

            if (body.has("metadata")) {
                val jsonArray = body.getJSONArray("metadata")
                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)

                    metadata[item.getString("key")] = item.getString("value")
                }
            }

            return metadata
        }
        set(value) {
            val jsonArray = JSONArray()

            value.forEach {
                jsonArray.put(
                    JSONObject()
                        .put("key", it.key)
                        .put("value", it.value)
                )
            }

            body.put("metadata", jsonArray)
        }

    var panoramic: Boolean
        get() = body.getBooleanOrDefault("panoramic", false)
        set(value) {
            body.put("panoramic", value)
        }

    var mp4Support: Boolean
        get() = body.getBooleanOrDefault("mp4Support", true)
        set(value) {
            body.put("mp4Support", value)
        }
}