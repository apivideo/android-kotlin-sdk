package video.api.androidkotlinsdk

import video.api.androidkotlinsdk.model.Error
import java.io.IOException

interface CallBack<T> {
    fun onError(error: Error)

    fun onFatal(e: IOException)

    fun onSuccess(result: T)
}