package video.api.androidkotlinsdk.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import video.api.androidkotlinsdk.http.TestCallback
import video.api.androidkotlinsdk.http.TestRequestExecutor
import video.api.androidkotlinsdk.model.Chapter
import video.api.androidkotlinsdk.pagination.Page
import java.io.File
import java.util.*

internal class ChapterApiTest {
    private val executor = TestRequestExecutor()
    private val api = ChapterApi("https://tests", executor)

    @Test
    fun upload() {
        val file = Mockito.mock(File::class.java)
        Mockito.`when`(file.path).thenReturn("file")

        val testCallBack = TestCallback<Chapter>()
        api.upload("chXXX", Locale.FRANCE, file, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/videos/chXXX/chapters/fr", lastRequest.url.toString())
    }

    @Test
    fun get() {
       val testCallBack = TestCallback<Chapter>()
        api.get("chXXX", Locale.FRANCE , testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals("https://tests/videos/chXXX/chapters/fr", lastRequest.url.toString())
    }

    @Test
    fun list() {
        val testCallBack = TestCallback<Page<Chapter>?>()
        api.list("chXXX",1, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals(
            "https://tests/videos/chXXX/chapters?currentPage=1&pageSize=25",
            lastRequest.url.toString()
        )
    }

    @Test
    fun listAllParam() {
        val testCallBack = TestCallback<Page<Chapter>?>()
        api.list("chXXX",1, 12, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals(
            "https://tests/videos/chXXX/chapters?currentPage=1&pageSize=12",
            lastRequest.url.toString()
        )
    }

    @Test
    fun delete() {
        api.delete("chXXX", Locale.FRENCH, TestCallback())
        val lastRequest = executor.lastRequest!!

        assertEquals("DELETE", lastRequest.method)
        assertEquals("https://tests/videos/chXXX/chapters/fr", lastRequest.url.toString())
    }

}