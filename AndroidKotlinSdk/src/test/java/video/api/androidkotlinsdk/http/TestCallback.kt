package video.api.androidkotlinsdk.http

import video.api.androidkotlinsdk.CallBack
import video.api.androidkotlinsdk.model.Error
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