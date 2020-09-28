package video.api.androidkotlinsdk.api

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import video.api.androidkotlinsdk.http.TestRequestExecutor
import video.api.androidkotlinsdk.model.Player
import video.api.androidkotlinsdk.http.TestCallback
import video.api.androidkotlinsdk.pagination.Page
import java.io.File

internal class PlayerApiTest {
    private val executor = TestRequestExecutor()
    private val api = PlayerApi("https://tests", executor)

    @Test
    fun create() {
        val testCallBack = TestCallback<Player>()
        api.create(Player(), testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/players", lastRequest.url.toString())
    }

    @Test
    fun update() {
        val player = Player(JSONObject().put("playerId", "liXXX"))

        val testCallback = TestCallback<Player>()
        api.update(player, testCallback)
        val lastRequest = executor.lastRequest!!

        assertEquals("PATCH", lastRequest.method)
        assertEquals("https://tests/players/liXXX", lastRequest.url.toString())
    }

    @Test
    fun list() {
        val testCallBack = TestCallback<Page<Player>?>()
        api.list(1, testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals(
            "https://tests/players?currentPage=1&pageSize=25",
            lastRequest.url.toString()
        )
    }

    @Test
    fun get() {
        val testCallBack = TestCallback<Player>()
        api.get("liXXX", testCallBack)

        val lastRequest = executor.lastRequest!!

        assertEquals("GET", lastRequest.method)
        assertEquals("https://tests/players/liXXX", lastRequest.url.toString())
    }

    @Test
    fun delete() {
        api.delete("liXXX", TestCallback())
        val lastRequest = executor.lastRequest!!

        assertEquals("DELETE", lastRequest.method)
        assertEquals("https://tests/players/liXXX", lastRequest.url.toString())
    }

    @Test
    fun uploadLogo() {
        val file = Mockito.mock(File::class.java)

        api.uploadLogo("ptXXX", file, "https://tests".toHttpUrl(), TestCallback())

        val lastRequest = executor.lastRequest!!

        assertEquals("POST", lastRequest.method)
        assertEquals("https://tests/players/ptXXX/logo", lastRequest.url.toString())
    }
}