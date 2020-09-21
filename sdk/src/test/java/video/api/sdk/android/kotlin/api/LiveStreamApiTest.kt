package video.api.sdk.android.kotlin.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import video.api.sdk.android.kotlin.http.TestCallback
import video.api.sdk.android.kotlin.http.TestRequestExecutor
import video.api.sdk.android.kotlin.model.LiveStream
import video.api.sdk.android.kotlin.pagination.Page
import java.io.File

internal class LiveStreamApiTest {
    private val executor = TestRequestExecutor()
    private val api = LiveStreamApi("https://tests", executor)

    @Test
    fun create() {
        val testCallBack = TestCallback<LiveStream>()
        api.create(LiveStream("foo"), testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/live-streams", lastRequest.url.toString())
    }

    @Test
    fun get() {
        val testCallBack = TestCallback<LiveStream>()
        api.get("liXXX", testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals("https://tests/live-streams/liXXX", lastRequest.url.toString())
    }

    @Test
    fun list() {
        val testCallBack = TestCallback<Page<LiveStream>?>()
        api.list(1, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals(
            "https://tests/live-streams?currentPage=1&pageSize=25",
            lastRequest.url.toString()
        )
    }

    @Test
    fun delete() {
        api.delete("liXXX", TestCallback())
        val lastRequest = executor.lastRequest!!

        assertEquals("DELETE", lastRequest.method)
        assertEquals("https://tests/live-streams/liXXX", lastRequest.url.toString())
    }


    @Test
    fun uploadThumbnail() {
        val file = Mockito.mock(File::class.java)

        api.uploadThumbnail("liXXX", file, TestCallback())

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/live-streams/liXXX/thumbnail", lastRequest.url.toString())
    }

    @Test
    fun deleteThumbnail() {
        api.deleteThumbnail("liXXX", TestCallback())
        val lastRequest = executor.lastRequest!!

        assertEquals("DELETE", lastRequest.method)
        assertEquals("https://tests/live-streams/liXXX/thumbnail", lastRequest.url.toString())
    }
}