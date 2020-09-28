package video.api.androidkotlinsdk.http

import okhttp3.Request
import video.api.androidkotlinsdk.CallBack
import video.api.androidkotlinsdk.model.BodyTransformer

class TestRequestExecutor: RequestExecutor{
    var lastRequest: Request? = null
    var lastCallback: Any? = null

    override fun <T> execute(
        request: Request,
        transformer: BodyTransformer<T>,
        callback: CallBack<T>
    ) {
        lastRequest = request
        lastCallback = callback
    }
}