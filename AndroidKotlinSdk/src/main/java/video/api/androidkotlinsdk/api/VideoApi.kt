package video.api.androidkotlinsdk.api

import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import video.api.androidkotlinsdk.CallBack
import video.api.androidkotlinsdk.RequestBodyUtil
import video.api.androidkotlinsdk.http.RequestExecutor
import video.api.androidkotlinsdk.model.*
import video.api.androidkotlinsdk.pagination.Page
import video.api.androidkotlinsdk.pagination.Pager
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.concurrent.CountDownLatch


class VideoApi(
    private val baseUri: String,
    private val executor: RequestExecutor,
    private val client: OkHttpClient
) {
    companion object {
        private val boundary: String = "----WebKitFormBoundary" + System.currentTimeMillis()
        var defaultChunkLength: Long = 100L
    }

    private val chunkLength = (1024L * 1000L) * defaultChunkLength

    private val videoTransformer = BodyTransformer {
        Video(it
            // `source` is string as input but object as output
            .put("sourceObject", it.get("source"))
            .apply {
                remove("source")
            }
        )
    }

    private val statusTransformer = BodyTransformer {
        Status(it)
    }

    /**
     * Create new video
     * @param video Video object
     * @param  callBack You need to override CallBack
     * @return JSONObject or Exception
     * @see CallBack use response as JSONObject
     */
    private fun create(video: Video, callBack: CallBack<Video>) {
        val url = "$baseUri/videos"
        val request = Request.Builder()
            .url(url)
            .post(video.body.toString().toRequestBody())
            .build()

        executor.execute(request, videoTransformer, callBack)
    }

    /**
     * @param  videoId Identifier of the video to be uploaded
     * @param  file local video file
     * @return JSONObject or Exception
     * @see    callBack use response as JSONObject
     */
    private fun uploadSmallFile(
        videoId: String,
        file: File,
        callBack: CallBack<Video>
    ) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", "file", file.asRequestBody())
            .build()

        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/source")
            .addHeader("Content-Type", "multipart/form-data; boundary=$boundary")
            .post(requestBody)
            .build()

        executor.execute(request, videoTransformer, callBack)
    }

    /**
     * Upload large file by chunks
     *
     * @param videoId
     * @param file
     */
    private fun uploadBigFile(videoId: String, file: File){
        val fileLength = file.length()
        try {
            Log.e("chunkLength", chunkLength.toString())
            var b = ByteArray(chunkLength.toInt())
            var bytesReads = 0

            for (offset in 0 until fileLength step chunkLength){
                var readBytes: Int
                val fileStream = file.inputStream()
                var currentPosition = (offset.toInt()) + chunkLength.toInt() - 1

                // foreach chunk except the first one
                if(offset > 0){
                    // skip all the chunks already uploaded
                    fileStream.skip(offset)
                }

                // if this is the last chunk
                if(currentPosition > fileLength){
                    readBytes = fileStream.read(b, 0, (fileLength - bytesReads).toInt())
                    currentPosition = file.length().toInt() - 1
                }else{
                    readBytes = fileStream.read(b, 0, chunkLength.toInt())
                }
                bytesReads += readBytes

                val byteArrayOutput = ByteArrayOutputStream()
                byteArrayOutput.write(b, 0, readBytes)
                val byteArrayInput = ByteArrayInputStream(byteArrayOutput.toByteArray())
                val videoFile = RequestBodyUtil.create(byteArrayInput)

                val body = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "file", videoFile)
                    .build()

                val request = Request.Builder()
                    .url("$baseUri/videos/$videoId/source")
                    .addHeader(
                        "Content-Range",
                        "bytes ${offset.toInt()}-$currentPosition/${file.length().toInt()}"
                    )
                    .post(body)
                    .build()

                val countDownLatch = CountDownLatch(1)

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        countDownLatch.countDown()
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        response.use {
                            if (!response.isSuccessful) throw IOException("Unexpected code $response")
                            countDownLatch.countDown()
                        }
                    }
                })
                countDownLatch.await()
                byteArrayOutput.close()
                byteArrayInput.close()
                fileStream.close()
            }
        }catch (e: Exception){
            Log.e("error post", e.toString())
        }
    }

    /**
     * Create and upload new video
     */
    fun upload(file: File, video: Video, callBack: CallBack<Video>) {
        create(video, object : CallBack<Video> {
            override fun onError(error: Error) {
                callBack.onError(error)
            }

            override fun onFatal(e: IOException) {
                callBack.onFatal(e)
            }

            override fun onSuccess(result: Video) {
                val videoId = result.videoId
                if (videoId != null) {
                    val size = file.length()
                    if (size < chunkLength) {
                        uploadSmallFile(videoId, file, callBack)
                    } else {
                        uploadBigFile(videoId, file)
                    }
                } else {
                    callBack.onFatal(IOException("Error during uploading!"))
                }
            }
        })
    }

    fun upload(file: File, title: String, callBack: CallBack<Video>) =
        upload(file, Video(title), callBack)

    fun upload(file: File, callBack: CallBack<Video>) =
        upload(file, file.name, callBack)


    fun list(pageNumber: Int, callBack: CallBack<Page<Video>?>) =
        list(pageNumber, QueryParams(), callBack)

    fun list(pageNumber: Int, pageSize: Int, callBack: CallBack<Page<Video>?>) =
        list(pageNumber, pageSize, QueryParams(), callBack)

    fun list(pageNumber: Int, params: QueryParams, callBack: CallBack<Page<Video>?>) =
        newPager(pageNumber, 25, params).next(callBack)

    fun list(
        pageNumber: Int,
        pageSize: Int,
        params: QueryParams,
        callBack: CallBack<Page<Video>?>
    ) = newPager(pageNumber, pageSize, params).next(callBack)


    private fun newPager(pageNumber: Int = 1, pageSize: Int, params: QueryParams): Pager<Video> {
        val urlBuilder = "$baseUri/videos".toHttpUrl().newBuilder()
        params.applyTo(urlBuilder)
        val baseRequest = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()

        return Pager(executor, baseRequest, videoTransformer, pageNumber, pageSize)
    }

    /**
     * Get Video by id
     *
     * @param videoId need it to search the video
     * @return Video or Exception
     */
    fun get(videoId: String, callBack: CallBack<Video>) {
        val request = Request.Builder()
            .url("$baseUri/videos/$videoId")
            .get()
            .build()

        executor.execute(request, videoTransformer, callBack)
    }


    /**
     * Get the video status
     * @param videoId need it to search the video status
     * @return Video or Exception / SdkResponse
     */
    fun getStatus(videoId: String, callBack: CallBack<Status>) {
        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/status")
            .get()
            .build()

        executor.execute(request, statusTransformer, callBack)
    }


    /**
     * Update information(s) of the video
     *
     * @param video need new Video object with the videoId
     * @return Video or Exception / SdkResponse
     */
    fun update(video: Video, callBack: CallBack<Video>) {
        val request = Request.Builder()
            .url("$baseUri/videos/${video.videoId}")
            .patch(video.body.toString().toRequestBody())
            .build()

        executor.execute(request, videoTransformer, callBack)
    }

    fun uploadThumbnail(videoId: String, file: File, callBack: CallBack<Video>) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", "file", file.asRequestBody())
            .build()

        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/thumbnail")
            .addHeader("Content-Type", "multipart/form-data; boundary=${boundary}")
            .post(requestBody)
            .build()

        executor.execute(request, videoTransformer, callBack)
    }

    fun deleteThumbnail(videoId: String, callback: CallBack<Boolean>) {
        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/thumbnail")
            .delete()
            .build()

        executor.execute(request, { true }, callback)
    }


    /**
     * Delete the video
     *
     * @param videoId Identifier of the video to delete
     * @return Boolean or Exception / SdkResponse
     */
    fun delete(videoId: String, callBack: CallBack<Boolean>) {
        val request = Request.Builder()
            .url("$baseUri/videos/$videoId")
            .delete()
            .build()

        executor.execute(request, { true }, callBack)
    }

}
