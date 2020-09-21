package video.api.sdk.android.kotlin

import video.api.sdk.android.kotlin.model.Error
import java.io.IOException

interface CallBack<T> {
    fun onError(error: Error)

    fun onFatal(e: IOException)

    fun onSuccess(result: T)
}