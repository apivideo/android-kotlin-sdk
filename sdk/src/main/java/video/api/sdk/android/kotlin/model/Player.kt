package video.api.sdk.android.kotlin.model

import org.json.JSONObject
import java.util.*

class Player(val body: JSONObject = JSONObject()) {

    class QueryParams : video.api.sdk.android.kotlin.model.QueryParams() {
        // Sort
        fun sortByCreatedAt(order: SortOrder = SortOrder.ASC) =
            sortBy("createdAt", order) as QueryParams

        fun sortByUpdatedAt(order: SortOrder = SortOrder.ASC) =
            sortBy("updatedAt", order) as QueryParams
    }

    class AssetsPlayer(
        val body: JSONObject
    ) {
        // Read
        val logo: String?
            get() = body.getStringOrNull("logo")

        //Read/Write
        var link: String?
            get() = body.getStringOrNull("link")
            set(value) {
                body.put("link", value)
            }

    }

    //Read
    val playerId: String?
        get() = body.getStringOrNull("playerId")

    val assets: AssetsPlayer?
        get() = if (body.has("assets")) {
            AssetsPlayer(body.getJSONObject("assets"))
        } else {
            null
        }

    val createdAt: Date?
        get() = body.getDateOrNull("createdAt")


    val updatedAt: Date?
        get() = body.getDateOrNull("updatedAt")


    //Read/Write

    var shapeMargin: Int?
        get() = body.getIntOrNull("shapeMargin")
        set(value) {
            body.put("shapeMargin", value)
        }

    var shapeRadius: Int?
        get() = body.getIntOrNull("shapeRadius")
        set(value) {
            body.put("shapeRadius", value)
        }


    var shapeAspect: String?
        get() = body.getStringOrNull("shapeAspect")
        set(value) {
            body.put("shapeAspect", value)
        }


    var shapeBackgroundTop: String?
        get() = body.getStringOrNull("shapeBackgroundTop")
        set(value) {
            body.put("shapeBackgroundTop", value)
        }


    var shapeBackgroundBottom: String?
        get() = body.getStringOrNull("shapeBackgroundBottom")
        set(value) {
            body.put("shapeBackgroundBottom", value)
        }


    var text: String?
        get() = body.getStringOrNull("text")
        set(value) {
            body.put("text", value)
        }


    var link: String?
        get() = body.getStringOrNull("link")
        set(value) {
            body.put("link", value)
        }

    var linkHover: String?
        get() = body.getStringOrNull("linkHover")
        set(value) {
            body.put("linkHover", value)
        }

    var linkActive: String?
        get() = body.getStringOrNull("linkActive")
        set(value) {
            body.put("linkActive", value)
        }

    var trackPlayed: String?
        get() = body.getStringOrNull("trackPlayed")
        set(value) {
            body.put("trackPlayed", value)
        }

    var trackUnplayed: String?
        get() = body.getStringOrNull(" trackUnplayed")
        set(value) {
            body.put("trackUnplayed", value)
        }

    var trackBackground: String?
        get() = body.getStringOrNull("trackBackground")
        set(value) {
            body.put("trackBackground", value)
        }


    var backgroundTop: String?
        get() = body.getStringOrNull("backgroundTop")
        set(value) {
            body.put("backgroundTop", value)
        }


    var backgroundBottom: String?
        get() = body.getStringOrNull("backgroundBottom")
        set(value) {
            body.put("backgroundBottom", value)
        }

    var backgroundText: String?
        get() = body.getStringOrNull("backgroundText")
        set(value) {
            body.put("backgroundText", value)
        }

    var enableApi: Boolean
        get() = if (body.has("enableApi")) body.getBoolean("enableApi") else true
        set(value) {
            body.put("enableApi", value)
        }

    var forceAutoplay: Boolean
        get() = if (body.has("forceAutoplay")) body.getBoolean("forceAutoplay") else false
        set(value) {
            body.put("forceAutoplay", value)
        }


    var hideTitle: Boolean
        get() = if (body.has("hideTitle")) body.getBoolean("hideTitle") else false
        set(value) {
            body.put("hideTitle", value)
        }

    var forceLoop: Boolean
        get() = if (body.has("forceLoop")) body.getBoolean("forceLoop") else false
        set(value) {
            body.put("forceLoop", value)
        }

    var enableControls: Boolean
        get() = if (body.has("enableControls")) body.getBoolean("enableControls") else true
        set(value) {
            body.put("enableControls", value)
        }
}