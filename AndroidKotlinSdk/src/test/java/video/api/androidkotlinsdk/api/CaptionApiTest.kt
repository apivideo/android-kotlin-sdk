package video.api.androidkotlinsdk.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import video.api.androidkotlinsdk.http.TestCallback
import video.api.androidkotlinsdk.http.TestRequestExecutor
import video.api.androidkotlinsdk.model.Caption
import video.api.androidkotlinsdk.pagination.Page
import java.io.File
import java.util.*

internal class CaptionApiTest {
    private val executor = TestRequestExecutor()
    private val api = CaptionApi("https://tests", executor)

    @Test
    fun upload() {
        val file = Mockito.mock(File::class.java)

        val testCallBack = TestCallback<Caption>()
        api.upload("caXXX", Locale.FRANCE, file, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/videos/caXXX/captions/fr", lastRequest.url.toString())
    }

    @Test
    fun get() {
        val testCallBack = TestCallback<Caption>()
        api.get("caXXX", Locale.FRANCE , testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals("https://tests/videos/caXXX/captions/fr", lastRequest.url.toString())
    }

    @Test
    fun list() {
        val testCallBack = TestCallback<Page<Caption>?>()
        api.list("caXXX", 1, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals(
            "https://tests/videos/caXXX/captions?currentPage=1&pageSize=25",
            lastRequest.url.toString()
        )
    }

    @Test
    fun listAllParam() {
        val testCallBack = TestCallback<Page<Caption>?>()
        api.list("caXXX", 1, 12, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals(
            "https://tests/videos/caXXX/captions?currentPage=1&pageSize=12",
            lastRequest.url.toString()
        )
    }

    @Test
    fun setDefault() {
        val testCallBack = TestCallback<Caption>()
        api.setDefault("caXXX", Locale.FRENCH, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("PATCH", lastRequest.method)
        assertEquals(
            "https://tests/videos/caXXX/captions/fr",
            lastRequest.url.toString()
        )
    }

    @Test
    fun delete() {
        api.delete("caXXX", Locale.FRENCH, TestCallback())
        val lastRequest = executor.lastRequest!!

        assertEquals("DELETE", lastRequest.method)
        assertEquals("https://tests/videos/caXXX/captions/fr", lastRequest.url.toString())
    }

}