package video.api.sdk.android.kotlin.api

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import video.api.sdk.android.kotlin.CallBack
import video.api.sdk.android.kotlin.http.RequestExecutor
import video.api.sdk.android.kotlin.model.BodyTransformer
import video.api.sdk.android.kotlin.model.Chapter
import video.api.sdk.android.kotlin.pagination.Page
import video.api.sdk.android.kotlin.pagination.Pager
import java.io.File
import java.util.*

class ChapterApi(
    private val baseUri: String,
    private val executor: RequestExecutor
) {

    private val chapterTransformer = BodyTransformer {
        Chapter(it)
    }

    /**
     * Upload Chapter
     * @param videoId select the video to upload chapter
     * @param filePath set the path of the local file
     * @param fileName set the name of the file
     * @param language set the language of the file ("en" for example)
     *
     * @return Chapter or Exception / SdkResponse
     */
    fun upload(videoId: String, language: Locale, file: File, callBack: CallBack<Chapter>) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", "file", file.asRequestBody(MEDIA_TYPE_VTT))
            .build()

        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/chapters/${language.language}")
            .addHeader("Content-Type", "multipart/form-data; boundary=${boundary}")
            .post(requestBody)
            .build()

        executor.execute(request, chapterTransformer, callBack)
    }

    fun get(videoId: String, language: Locale, callBack: CallBack<Chapter>) {
        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/chapters/${language.language}")
            .get()
            .build()

        executor.execute(request, chapterTransformer, callBack)
    }


    fun list(videoId: String, pageNumber: Int, callBack: CallBack<Page<Chapter>?>) =
        newPager(pageNumber, 25, videoId).next(callBack)

    fun list(videoId: String, pageNumber: Int, pageSize: Int, callBack: CallBack<Page<Chapter>?>) =
        newPager(pageNumber, pageSize, videoId).next(callBack)


    private fun newPager(pageNumber: Int = 1, pageSize: Int, videoId: String): Pager<Chapter> {
        val urlBuilder = "$baseUri/videos/$videoId/chapters".toHttpUrl().newBuilder()
        val baseRequest = Request.Builder()
            .url(urlBuilder.build())
            .get()
            .build()

        return Pager(executor, baseRequest, chapterTransformer, pageNumber, pageSize)
    }

    fun delete(videoId: String, language: Locale, callBack: CallBack<Boolean>) {
        val request = Request.Builder()
            .url("$baseUri/videos/$videoId/chapters/$language")
            .addHeader("Content-Type", "multipart/form-data; boundary=${boundary}")
            .delete()
            .build()

        executor.execute(request, { true }, callBack)
    }

    companion object {
        private val boundary: String = "----WebKitFormBoundary" + System.currentTimeMillis()
        private val MEDIA_TYPE_VTT = "text/vtt".toMediaType()
    }
}