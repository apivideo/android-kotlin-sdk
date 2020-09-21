package video.api.sdk.android.kotlin.http

import okhttp3.Request
import video.api.sdk.android.kotlin.CallBack
import video.api.sdk.android.kotlin.model.BodyTransformer

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