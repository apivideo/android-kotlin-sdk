package video.api.sdk.android.kotlin.model

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class VideoTest {
    private val responseVideo = Video(
        JSONObject()
            .put("videoId", "viXXX")
            .put("publishedAt", "2020-09-10T14:51:52+00:00")
            .put(
                // This attribute is not in the actual response but is copied from
                // `source` attribute (input as string, output as object)
                // @see VideoApi.videoTransformer
                "sourceObject",
                JSONObject()
            )
            .put("assets", JSONObject())
    )

    @Test
    fun videoId() {
        assertNull(Video().videoId)
        assertNotNull(responseVideo.videoId)
    }

    @Test
    fun publishedAt() {
        assertNull(Video().publishedAt)
        assertNotNull(responseVideo.publishedAt)
    }

    @Test
    fun getAssets() {
        assertNull(Video().assets)
        assertNotNull(responseVideo.assets)
    }

    @Test
    fun getSourceObject() {
        assertNull(Video().sourceObject)
        assertNotNull(responseVideo.sourceObject)
    }

    @Test
    fun title() {
        val video = Video()
        assertNull(video.title)

        video.title = "foo"
        assertNotNull(video.title)
        assertEquals(
            JSONObject().put("title", "foo").toString(),
            video.body.toString()
        )
    }

    @Test
    fun source() {
        val video = Video()
        assertNull(video.source)

        video.source = "https://api.video/".toHttpUrl()
        assertNotNull(video.source)
        assertEquals(
            JSONObject().put("source", "https://api.video/").toString(),
            video.body.toString()
        )
    }

    @Test
    fun description() {
        val video = Video()
        assertNull(video.description)

        video.description = "foo"
        assertNotNull(video.description)
        assertEquals(
            JSONObject().put("description", "foo").toString(),
            video.body.toString()
        )
    }

    @Test
    fun playerId() {
        val video = Video()
        assertNull(video.playerId)

        video.playerId = "foo"
        assertNotNull(video.playerId)
        assertEquals(
            JSONObject().put("playerId", "foo").toString(),
            video.body.toString()
        )
    }

    @Test
    fun public() {
        val video = Video()
        // Default
        assertTrue(video.public)
        assertFalse(video.body.has("public"))

        // False
        video.public = false
        assertFalse(video.public)
        assertEquals(
            JSONObject().put("public", false).toString(),
            video.body.toString()
        )

        // True
        video.public = true
        assertTrue(video.public)
        assertEquals(
            JSONObject().put("public", true).toString(),
            video.body.toString()
        )
    }

    @Test
    fun tags() {
        val video = Video()
        assertEquals(0, video.tags.size)

        val tagSet = setOf("a", "b")

        video.tags = tagSet
        assertEquals(tagSet, video.tags)
        assertEquals(
            JSONObject().put("tags", JSONArray(tagSet)).toString(),
            video.body.toString()
        )
    }

    @Test
    fun metadata() {
        val video = Video()
        assertEquals(0, video.metadata.size)

        val metadataMap = mapOf(Pair("a", "b"), Pair("c", "d"))

        video.metadata = metadataMap

        assertEquals(metadataMap, video.metadata)
        assertEquals(
            JSONObject().put(
                "metadata",
                JSONArray()
                    .put(
                        JSONObject()
                            .put("key", "a").put("value", "b")
                    )
                    .put(
                        JSONObject()
                            .put("key", "c").put("value", "d")
                    )
            ).toString(),
            video.body.toString()
        )
    }

    @Test
    fun panoramic() {
        val video = Video()
        // Default
        assertFalse(video.panoramic)
        assertFalse(video.body.has("panoramic"))

        // False
        video.panoramic = false
        assertFalse(video.panoramic)
        assertEquals(
            JSONObject().put("panoramic", false).toString(),
            video.body.toString()
        )

        // True
        video.panoramic = true
        assertTrue(video.panoramic)
        assertEquals(
            JSONObject().put("panoramic", true).toString(),
            video.body.toString()
        )
    }

    @Test
    fun mp4Support() {
        val video = Video()
        // Default
        assertTrue(video.mp4Support)
        assertFalse(video.body.has("mp4Support"))

        // False
        video.mp4Support = false
        assertFalse(video.mp4Support)
        assertEquals(
            JSONObject().put("mp4Support", false).toString(),
            video.body.toString()
        )

        // True
        video.mp4Support = true
        assertTrue(video.mp4Support)
        assertEquals(
            JSONObject().put("mp4Support", true).toString(),
            video.body.toString()
        )
    }

    @Nested
    inner class QueryParamsTest {
        @Test
        fun description() {
            val q = Video.QueryParams()
                .description("a")

            assertEquals(
                mapOf(Pair("description", "a")),
                q.stringParams
            )
        }

        @Test
        fun liveStreamId() {
            val q = Video.QueryParams()
                .liveStreamId("liXXX")

            assertEquals(
                mapOf(Pair("liveStreamId", "liXXX")),
                q.stringParams
            )
        }

        @Test
        fun title() {
            val q = Video.QueryParams()
                .title("a")

            assertEquals(
                mapOf(Pair("title", "a")),
                q.stringParams
            )
        }

        @Test
        fun tags() {
            val q = Video.QueryParams()
                .tags(setOf("a", "b"))

            assertEquals(
                mapOf(
                    Pair("tags", setOf("a", "b"))
                ), q.setParams
            )
        }

        @Test
        fun metadata() {
            val q = Video.QueryParams()
                .metadata(
                    mapOf(
                        Pair("bar", "a"),
                        Pair("baz", "b")
                    )
                )

            assertEquals(
                mapOf(
                    Pair(
                        "metadata", mapOf(
                            Pair("bar", "a"),
                            Pair("baz", "b")
                        )
                    )
                ), q.mapParams
            )
        }

        @Test
        fun sortByPublishedAt() {
            val default = Video.QueryParams()
                .sortByPublishedAt()

            assertEquals(
                mapOf(
                    Pair("sortBy", "publishedAt"),
                    Pair("sortOrder", "asc")
                ), default.stringParams
            )

            val asc = Video.QueryParams()
                .sortByPublishedAt(QueryParams.SortOrder.ASC)

            assertEquals(
                mapOf(
                    Pair("sortBy", "publishedAt"),
                    Pair("sortOrder", "asc")
                ), asc.stringParams
            )

            val desc = Video.QueryParams()
                .sortByPublishedAt(QueryParams.SortOrder.DESC)

            assertEquals(
                mapOf(
                    Pair("sortBy", "publishedAt"),
                    Pair("sortOrder", "desc")
                ), desc.stringParams
            )
        }

        @Test
        fun sortByTitle() {
            val default = Video.QueryParams()
                .sortByTitle()

            assertEquals(
                mapOf(
                    Pair("sortBy", "title"),
                    Pair("sortOrder", "asc")
                ), default.stringParams
            )

            val asc = Video.QueryParams()
                .sortByTitle(QueryParams.SortOrder.ASC)

            assertEquals(
                mapOf(
                    Pair("sortBy", "title"),
                    Pair("sortOrder", "asc")
                ), asc.stringParams
            )

            val desc = Video.QueryParams()
                .sortByTitle(QueryParams.SortOrder.DESC)

            assertEquals(
                mapOf(
                    Pair("sortBy", "title"),
                    Pair("sortOrder", "desc")
                ), desc.stringParams
            )
        }
    }

    @Nested
    inner class SourceTest {
        @Test
        fun uri() {
            val source = Video.Source(JSONObject())
            assertNull(source.uri)

            source.body.put("uri", "https://api.video/")
            assertNotNull(source.uri)
            assertEquals(
                JSONObject().put("uri", "https://api.video/").toString(),
                source.body.toString()
            )
        }

        @Test
        fun type() {
            val source = Video.Source(
                JSONObject()
                    .put("type", "upload")
            )

            assertNotNull(source.type)
            assertEquals(
                JSONObject().put("type", "upload").toString(),
                source.body.toString()
            )
        }

        @Test
        fun liveStream() {
            val source = Video.Source(JSONObject())
            assertNull(source.liveStream)

            source.body.put("liveStream", JSONObject())
            assertNotNull(source.liveStream)
        }

        @Nested
        inner class LiveStreamSourceTest {
            @Test
            fun liveStreamId() {
                val liveStreamSource = Video.Source.LiveStreamSource(
                    JSONObject()
                        .put("liveStreamId", "liXXX")
                )

                assertNotNull(liveStreamSource.liveStreamId)
            }

            @Test
            fun links() {
                val liveStreamSource = Video.Source.LiveStreamSource(
                    JSONObject()
                        .put(
                            "links", JSONArray()
                                .put(
                                    JSONObject()
                                        .put("rel", "self")
                                        .put("uri", "https://api.video/")
                                )
                        )
                )

                assertEquals(1, liveStreamSource.links.size)
                liveStreamSource.links.forEach {
                    assertEquals("self", it.rel)
                    assertEquals("https://api.video/", it.uri.toString())
                }
            }

        }
    }

    @Nested
    inner class AssetsTest {
        private val responseAssets = Assets(
            JSONObject()
                .put(
                    "iframe",
                    "<iframe src=\"https://api.video/\" width=\"100%\" height=\"100%\" frameborder=\"0\" scrolling=\"no\" allowfullscreen=\"\"></iframe>"
                )
                .put("player", "https://api.video/")
                .put("hls", "https://api.video/")
                .put("thumbnail", "https://api.video/")
                .put("mp4", "https://api.video/")
        )

        @Test
        fun hls() {
            assertNotNull(responseAssets.hls)
        }

        @Test
        fun iFrame() {
            assertNotNull(responseAssets.iFrame)
        }

        @Test
        fun player() {
            assertNotNull(responseAssets.player)
        }

        @Test
        fun thumbnail() {
            assertNotNull(responseAssets.thumbnail)
        }
        @Test
        fun mp4() {
            assertNotNull(responseAssets.mp4)
        }
    }
}