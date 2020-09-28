package video.api.androidkotlinsdk.pagination

import okhttp3.Request
import video.api.androidkotlinsdk.CallBack
import video.api.androidkotlinsdk.http.RequestExecutor
import video.api.androidkotlinsdk.model.BodyTransformer

class Pager<T>(
    private val executor: RequestExecutor,
    private val baseRequest: Request,
    private val transformer: BodyTransformer<T>,
    private var currentPageNumber: Int = 1,
    private val pageSize: Int
) {
    fun next(callBack: CallBack<Page<T>?>) {
        val url = baseRequest.url.newBuilder()
            .setQueryParameter("currentPage", currentPageNumber.toString())
            .setQueryParameter("pageSize", pageSize.toString())
            .build()

        val request = baseRequest.newBuilder()
            .url(url)
            .build()

        val transformer = BodyTransformer { body ->
                currentPageNumber ++
                val page = Page(body, transformer)

                if (page.itemCount() == 0) {
                    null
                } else {
                    page
                }
            }

        executor.execute(request, transformer, callBack)
    }

}