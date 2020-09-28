package video.api.androidkotlinsdk.api

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import video.api.androidkotlinsdk.CallBack
import video.api.androidkotlinsdk.http.RequestExecutor
import video.api.androidkotlinsdk.model.BodyTransformer
import video.api.androidkotlinsdk.model.LiveStream
import video.api.androidkotlinsdk.model.QueryParams
import video.api.androidkotlinsdk.pagination.Page
import video.api.androidkotlinsdk.pagination.Pager
import java.io.File


class LiveStreamApi(
    private val baseUri: String,
    private val executor: RequestExecutor
) {
    companion object {
        private val boundary: String = "----WebKitFormBoundary" + System.currentTimeMillis()
    }

    private val liveStreamTransformer = BodyTransformer {
        LiveStream(it)
    }

    /**
     * Create LiveStream
     * @param name name of the object to create
     */
    fun create(name: String, callback: CallBack<LiveStream>) {
        create(LiveStream(name), callback)
    }

    /**
     * Create LiveStream
     * @param liveStream object to create
     */
    fun create(liveStream: LiveStream, callback: CallBack<LiveStream>) {
        val request = Request.Builder()
            .url("$baseUri/live-streams")
            .post(liveStream.body.toString().toRequestBody())
            .build()

        executor.execute(request, liveStreamTransformer, callback)
    }

    /**
     * Get LiveStream by Id
     * @param liveStreamId need the id to search the correct live stream
     * @return LiveStream or Exception / SdkResponse
     */
    fun get(liveStreamId: String, callback: CallBack<LiveStream>) {
        val request = Request.Builder()
            .url("$baseUri/live-streams/$liveStreamId")
            .get()
            .build()

        executor.execute(request, liveStreamTransformer, callback)
    }

    /**
     * List live streams at page `pageNumber`
     *
     * @param pageNumber
     * @param callBack
     */
    fun list(pageNumber: Int, callBack: CallBack<Page<LiveStream>?>) =
        list(pageNumber, QueryParams(), callBack)

    fun list(pageNumber: Int, pageSize: Int, callBack: CallBack<Page<LiveStream>?>) =
        list(pageNumber, pageSize, QueryParams(), callBack)

    fun list(pageNumber: Int, params: QueryParams, callBack: CallBack<Page<LiveStream>?>) =
        newPager(pageNumber, 25, params).next(callBack)

    /**
     * List live streams at page `pageNumber`, filtered/sorted on `params`
     * with a maximum of `pageSize` items
     *
     * @param pageNumber
     * @param pageSize
     * @param params
     * @param callBack
     */
    fun list(
        pageNumber: Int,
        pageSize: Int,
        params: QueryParams,
        callBack: CallBack<Page<LiveStream>?>
    ) =
        newPager(pageNumber, pageSize, params).next(callBack)


    private fun newPager(
        pageNumber: Int = 1,
        pageSize: Int,
        params: QueryParams
    ): Pager<LiveStream> {
        val urlBuilder = "$baseUri/live-streams".toHttpUrl().newBuilder()
        params.applyTo(urlBuilder)
        val baseRequest = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()

        return Pager(executor, baseRequest, liveStreamTransformer, pageNumber, pageSize)
    }

    /**
     * Delete LiveStream
     * @param liveStreamId select the live stream to delete
     * @return Boolean or Exception / SdkResponse
     */
    fun delete(liveStreamId: String, callback: CallBack<Boolean>) {
        val request = Request.Builder()
            .url("$baseUri/live-streams/$liveStreamId")
            .delete()
            .build()

        executor.execute(request, { true }, callback)
    }

    fun uploadThumbnail(liveStreamId: String, file: File, callBack: CallBack<LiveStream>) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", "file", file.asRequestBody())
            .build()

        val request = Request.Builder()
            .url("$baseUri/live-streams/$liveStreamId/thumbnail")
            .addHeader("Content-Type", "multipart/form-data; boundary=${boundary}")
            .post(requestBody)
            .build()

        executor.execute(request, liveStreamTransformer, callBack)
    }

    fun deleteThumbnail(liveStreamId: String, callback: CallBack<Boolean>) {
        val request = Request.Builder()
            .url("$baseUri/live-streams/$liveStreamId/thumbnail")
            .delete()
            .build()

        executor.execute(request, { true }, callback)
    }
}
