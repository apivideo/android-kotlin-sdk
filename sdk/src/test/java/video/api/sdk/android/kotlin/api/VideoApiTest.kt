package video.api.sdk.android.kotlin.api

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import video.api.sdk.android.kotlin.CallBack
import video.api.sdk.android.kotlin.http.TestCallback
import video.api.sdk.android.kotlin.http.TestRequestExecutor
import video.api.sdk.android.kotlin.model.Status
import video.api.sdk.android.kotlin.model.Video
import video.api.sdk.android.kotlin.pagination.Page
import java.io.File


internal class VideoApiTest {
    private val executor = TestRequestExecutor()
    private val api = VideoApi("https://tests", executor)

    @Test
    fun uploadSuccess() {
        val file = Mockito.mock(File::class.java)
        Mockito.`when`(file.name).thenReturn("file")

        val testCallBack = TestCallback<Video>()
        api.upload(file, testCallBack)

        var lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/videos", lastRequest.url.toString())

        val video = Video(JSONObject().put("videoId", "viXXX"))

        @Suppress("UNCHECKED_CAST")
        (executor.lastCallback as CallBack<Video>).onSuccess(video)

        lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/videos/viXXX/source", lastRequest.url.toString())

    }

    @Test
    fun uploadFatalWhenNoVideoId() {
        val file = Mockito.mock(File::class.java)

        val testCallBack = TestCallback<Video>()
        api.upload(file, "toto", testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/videos", lastRequest.url.toString())

        @Suppress("UNCHECKED_CAST")
        (executor.lastCallback as CallBack<Video>).onSuccess(Video())

        assertNotNull(testCallBack.lastFatal)
    }

    @Test
    fun list() {
        val testCallBack = TestCallback<Page<Video>?>()
        api.list(1, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals(
            "https://tests/videos?currentPage=1&pageSize=25",
            lastRequest.url.toString()
        )
    }
    
    @Test
    fun listAllParam() {
        val testCallBack = TestCallback<Page<Video>?>()
        api.list(1, 12, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals(
            "https://tests/videos?currentPage=1&pageSize=12",
            lastRequest.url.toString()
        )
    }

    @Test
    fun get() {
        val testCallBack = TestCallback<Video>()
        api.get("viXXX", testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals("https://tests/videos/viXXX", lastRequest.url.toString())
    }

    @Test
    fun getStatus() {
        val testCallback = TestCallback<Status>()
        api.getStatus("viXXX", testCallback)
        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals("https://tests/videos/viXXX/status", lastRequest.url.toString())
    }

    @Test
    fun update() {
        val video = Video(JSONObject().put("videoId", "viXXX"))
        val testCallback = TestCallback<Video>()
        api.update(video, testCallback)
        val lastRequest = executor.lastRequest!!

        assertEquals("PATCH", lastRequest.method)
        assertEquals("https://tests/videos/viXXX", lastRequest.url.toString())
    }
    
    @Test
    fun uploadThumbnail() {
        val file = Mockito.mock(File::class.java)

        api.uploadThumbnail("viXXX", file, TestCallback())

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/videos/viXXX/thumbnail", lastRequest.url.toString())
    }

    @Test
    fun deleteThumbnail() {
        api.deleteThumbnail("viXXX", TestCallback())
        val lastRequest = executor.lastRequest!!

        assertEquals("DELETE", lastRequest.method)
        assertEquals("https://tests/videos/viXXX/thumbnail", lastRequest.url.toString())
    }
    
    @Test
    fun delete() {
        api.delete("viXXX", TestCallback())
        val lastRequest = executor.lastRequest!!

        assertEquals("DELETE", lastRequest.method)
        assertEquals("https://tests/videos/viXXX", lastRequest.url.toString())
    }
}