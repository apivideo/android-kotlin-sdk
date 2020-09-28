package video.api.androidkotlinsdk.model

import okhttp3.HttpUrl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class QueryParamsTest {
    @Test
    fun setSingle() {
        val q = QueryParams()
            .setSingle("foo", "a")
            .setSingle("bar", "b")

        assertEquals(
            mapOf(
                Pair("foo", "a"),
                Pair("bar", "b")
            ), q.stringParams
        )
    }

    @Test
    fun setSet() {
        val q = QueryParams()
            .setSet("foo", setOf("a", "b"))

        assertEquals(
            mapOf(
                Pair("foo", setOf("a", "b"))
            ), q.setParams
        )
    }

    @Test
    fun setMap() {
        val q = QueryParams()
            .setMap(
                "foo", mapOf(
                    Pair("bar", "a"),
                    Pair("baz", "b")
                )
            )

        assertEquals(
            mapOf(
                Pair(
                    "foo", mapOf(
                        Pair("bar", "a"),
                        Pair("baz", "b")
                    )
                )
            ), q.mapParams
        )
    }

    @Test
    fun sortBy() {
        val default = QueryParams()
            .sortBy("title")

        assertEquals(
            mapOf(
                Pair("sortBy", "title"),
                Pair("sortOrder", "asc")
            ), default.stringParams
        )

        val asc = QueryParams()
            .sortBy("title", QueryParams.SortOrder.ASC)

        assertEquals(
            mapOf(
                Pair("sortBy", "title"),
                Pair("sortOrder", "asc")
            ), asc.stringParams
        )
        val desc = QueryParams()
            .sortBy("title", QueryParams.SortOrder.DESC)

        assertEquals(
            mapOf(
                Pair("sortBy", "title"),
                Pair("sortOrder", "desc")
            ), desc.stringParams
        )
    }

    @Test
    fun pageSize() {
        val q = QueryParams()
            .pageSize(4)

        assertEquals(
            mapOf(
                Pair("pageSize", "4")
            ), q.stringParams
        )
    }

    @Test
    fun applyTo() {
        val q = QueryParams()
            .setSingle("title", "a")
            .setSingle("title", "b")
            .setSet("tags", setOf("c", "d"))
            .setMap("metadata", mapOf(Pair("a", "b"), Pair("c", "d")))
            .sortBy("title", QueryParams.SortOrder.DESC)
            .pageSize(4)

        val builder = HttpUrl.Builder()

        q.applyTo(builder)

        assertEquals(
            "///?title=b&sortBy=title&sortOrder=desc&pageSize=4&tags[]=c&tags[]=d&metadata[a]=b&metadata[c]=d",
            builder.toString()
        )
    }
}