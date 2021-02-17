package video.api.androidkotlinsdk.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import video.api.androidkotlinsdk.http.TestCallback
import video.api.androidkotlinsdk.http.TestRequestExecutor
import video.api.androidkotlinsdk.model.LiveStream
import video.api.androidkotlinsdk.pagination.Page
import java.io.File

internal class LiveStreamApiTest {
    private val executor = TestRequestExecutor()
    private val api = LiveStreamApi("https://tests", executor)

    @Test
    fun create() {
        val testCallBack = TestCallback<LiveStream>()
        api.create(LiveStream("foo", true), testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/live-streams", lastRequest.url.toString())
    }

    @Test
    fun createName() {
        val testCallBack = TestCallback<LiveStream>()
        api.create("foo", testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/live-streams", lastRequest.url.toString())
    }

    @Test
    fun createPrivate() {
        val testCallBack = TestCallback<LiveStream>()
        api.createPrivate("foo", testCallBack)

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