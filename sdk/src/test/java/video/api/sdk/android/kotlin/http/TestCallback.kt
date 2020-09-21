package video.api.sdk.android.kotlin.http

import video.api.sdk.android.kotlin.CallBack
import video.api.sdk.android.kotlin.model.Error
import java.io.IOException

class TestCallback<T> : CallBack<T> {
    var lastResult: T? = null
    var lastError: Error? = null
    var lastFatal: IOException? = null

    override fun onError(error: Error) {
        lastError = error
    }

    override fun onFatal(e: IOException) {
        lastFatal = e
    }

    override fun onSuccess(result: T) {
        lastResult = result
    }
}