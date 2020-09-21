package video.api.sdk.android.kotlin.api

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import video.api.sdk.android.kotlin.CallBack
import video.api.sdk.android.kotlin.http.RequestExecutor
import video.api.sdk.android.kotlin.model.BodyTransformer
import video.api.sdk.android.kotlin.model.Caption
import video.api.sdk.android.kotlin.pagination.Page
import video.api.sdk.android.kotlin.pagination.Pager
import java.io.File
import java.util.*

class CaptionApi(
    private val baseUri: String,
    private val executor: RequestExecutor
) {
    private val captionTransformer = BodyTransformer {
        Caption(it)
    }

    fun upload(videoId: String, language: Locale, file: File, callBack: CallBack<Caption>) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", "file", file.asRequestBody(MEDIA_TYPE_VTT))
            .build()

        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/captions/${language.language}")
            .addHeader("Content-Type", "multipart/form-data; boundary=${boundary}")
            .post(requestBody)
            .build()

        executor.execute(request, captionTransformer, callBack)
    }

    fun get(videoId: String, language: Locale, callBack: CallBack<Caption>) {
        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/captions/${language.language}")
            .get()
            .build()

        executor.execute(request, captionTransformer, callBack)
    }

    fun list(videoId: String, pageNumber: Int, callBack: CallBack<Page<Caption>?>) =
        newPager(pageNumber, 25, videoId).next(callBack)

    fun list(
        videoId: String,
        pageNumber: Int,
        pageSize: Int,
        callBack: CallBack<Page<Caption>?>
    ) =
        newPager(pageNumber, pageSize, videoId).next(callBack)


    private fun newPager(pageNumber: Int = 1, pageSize: Int, videoId: String): Pager<Caption> {
        val urlBuilder = "$baseUri/videos/$videoId/captions".toHttpUrl().newBuilder()
        val baseRequest = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()

        return Pager(executor, baseRequest, captionTransformer, pageNumber, pageSize)
    }


    fun setDefault(videoId: String, language: Locale, callBack: CallBack<Caption>) {
        val postBody = JSONObject()
            .put("default", true)

        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/captions/$language")
            .patch(postBody.toString().toRequestBody())
            .build()

        executor.execute(request, captionTransformer, callBack)
    }

    fun delete(videoId: String, language: Locale, callBack: CallBack<Boolean>) {
        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/captions/$language")
            .addHeader("Content-Type", "multipart/form-data; boundary=$boundary")
            .delete()
            .build()

        executor.execute(request, { true }, callBack)
    }

    companion object {
        private val boundary: String = "----WebKitFormBoundary" + System.currentTimeMillis()
        private val MEDIA_TYPE_VTT = "text/vtt".toMediaType()
    }
}