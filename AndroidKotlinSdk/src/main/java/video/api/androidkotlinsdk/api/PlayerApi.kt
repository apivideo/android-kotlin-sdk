package video.api.androidkotlinsdk.api

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import video.api.androidkotlinsdk.CallBack
import video.api.androidkotlinsdk.http.RequestExecutor
import video.api.androidkotlinsdk.model.BodyTransformer
import video.api.androidkotlinsdk.model.Player
import video.api.androidkotlinsdk.model.QueryParams
import video.api.androidkotlinsdk.pagination.Page
import video.api.androidkotlinsdk.pagination.Pager
import java.io.File

class PlayerApi(
    private val baseUri: String,
    private val executor: RequestExecutor
) {
    private val playerTransformer = BodyTransformer {
        Player(it)
    }

    /**
     * Create Player
     *
     * @param player need new Player object to create it
     * @return Player or Exception / SdkResponse
     */
    fun create(player: Player, callBack: CallBack<Player>) {
        val request = Request.Builder()
            .url("$baseUri/players")
            .post(player.toString().toRequestBody())
            .build()

        executor.execute(request, playerTransformer, callBack)
    }

    /**
     * Update player
     * @param player pass new object with the id of the player who need to be updated
     */
    fun update(player: Player, callBack: CallBack<Player>) {
        val request = Request.Builder()
            .url("$baseUri/players/${player.playerId}")
            .patch(player.body.toString().toRequestBody())
            .build()

        executor.execute(request, playerTransformer, callBack)
    }

    /**
     * Get List of Player
     * @return MutableList<Player> or Exception / SdkResponse
     */

    fun list(pageNumber: Int, callBack: CallBack<Page<Player>?>) =
        list(pageNumber, QueryParams(), callBack)

    fun list(pageNumber: Int, pageSize: Int, callBack: CallBack<Page<Player>?>) =
        list(pageNumber, pageSize, QueryParams(), callBack)

    fun list(pageNumber: Int, params: QueryParams, callBack: CallBack<Page<Player>?>) =
        newPager(pageNumber, 25, params).next(callBack)

    fun list(
        pageNumber: Int,
        pageSize: Int,
        params: QueryParams,
        callBack: CallBack<Page<Player>?>
    ) =
        newPager(pageNumber, pageSize, params).next(callBack)


    private fun newPager(pageNumber: Int = 1, pageSize: Int, params: QueryParams): Pager<Player> {
        val urlBuilder = "$baseUri/players".toHttpUrl().newBuilder()
        params.applyTo(urlBuilder)
        val baseRequest = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()

        return Pager(executor, baseRequest, playerTransformer, pageNumber, pageSize)
    }

    fun get(playerId: String, callBack: CallBack<Player>) {
        val request = Request.Builder()
            .url("$baseUri/players/${playerId}")
            .get()
            .build()

        executor.execute(request, playerTransformer, callBack)
    }

    /**
     * Delete player
     */
    fun delete(playerId: String, callBack: CallBack<Boolean>) {
        val request = Request.Builder()
            .url("$baseUri/players/$playerId")
            .addHeader("Content-Type", "multipart/form-data; boundary=${boundary}")
            .delete()
            .build()

        executor.execute(request, { true }, callBack)
    }

    /**
     * Upload Logo
     * @param playerId player identifier
     * @param file logo file
     * @param link set the link of the logo
     * @return Boolean or Exception / SdkResponse
     */
    fun uploadLogo(
        playerId: String,
        file: File,
        link: HttpUrl,
        callBack: CallBack<Boolean>
    ) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("link", link.toString())
            .addFormDataPart("file", "file", file.asRequestBody(MEDIA_TYPE_PNG))
            .build()

        val request = Request.Builder()
            .url("$baseUri/players/$playerId/logo")
            .addHeader("Content-Type", "multipart/form-data; boundary=${boundary}")
            .post(requestBody)
            .build()

        executor.execute(request, { true }, callBack)
    }

    companion object {
        private val boundary: String = "----WebKitFormBoundary" + System.currentTimeMillis()
        private val MEDIA_TYPE_PNG = "image/png".toMediaType()
    }
}