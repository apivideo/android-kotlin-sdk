package video.api.androidkotlinsdk.model

import org.json.JSONObject

class Status(val body: JSONObject) {

    class Ingest(val body: JSONObject) {

        class ByteRange(val body: JSONObject) {
            val from: Int?
                get() = body.getIntOrNull("from")

            val to: Int?
                get() = body.getIntOrNull("to")

            val total: Int?
                get() = body.getIntOrNull("total")
        }

        val status: String?
            get() = body.getStringOrNull("status")

        val fileSize: Int?
            get() = body.getIntOrNull("filesize")

        val receivedBytes: List<ByteRange>
            get() {
                val qualities = mutableListOf<ByteRange>()

                body.getJSONArray("receivedBytes").iterator<JSONObject>().forEach {
                    qualities.add(ByteRange(it))
                }

                return qualities
            }
    }

    class Encoding(val body: JSONObject) {

        class Quality(val body: JSONObject) {
            val type: String?
                get() = body.getStringOrNull("type")

            val quality: String?
                get() = body.getStringOrNull("quality")

            val status: String?
                get() = body.getStringOrNull("status")
        }

        class Metadata(val body: JSONObject) {
            val width: Int?
                get() = body.getIntOrNull("width")

            val height: Int?
                get() = body.getIntOrNull("height")

            val bitrate: Double?
                get() = body.getDoubleOrNull("bitrate")

            val duration: Int?
                get() = body.getIntOrNull("duration")

            val framerate: Int?
                get() = body.getIntOrNull("framerate")

            val samplerate: Int?
                get() = body.getIntOrNull("samplerate")

            val videoCodec: String?
                get() = body.getStringOrNull("videoCodec")

            val audioCodec: String?
                get() = body.getStringOrNull("audioCodec")

            val aspectRatio: String?
                get() = body.getStringOrNull("aspectRatio")
        }

        val playable: Boolean
            get() = if (body.has("playable")) body.getBoolean("playable") else false

        val qualities: List<Quality>
            get() {
                val qualities = mutableListOf<Quality>()

                body.getJSONArray("qualities").iterator<JSONObject>().forEach {
                    qualities.add(Quality(it))
                }

                return qualities
            }

        val metadata: Metadata?
            get() = if (body.has("metadata")) {
                Metadata(body.getJSONObject("metadata"))
            } else {
                null
            }
    }

    val ingest: Ingest?
        get() = if (body.has("ingest")) {
            Ingest(body.getJSONObject("ingest"))
        } else {
            null
        }

    val encoding: Encoding?
        get() = if (body.has("encoding")) {
            Encoding(body.getJSONObject("encoding"))
        } else {
            null
        }
}