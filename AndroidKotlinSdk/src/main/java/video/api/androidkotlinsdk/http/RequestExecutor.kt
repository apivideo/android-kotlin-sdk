package video.api.androidkotlinsdk.http

import okhttp3.Request
import video.api.androidkotlinsdk.CallBack
import video.api.androidkotlinsdk.model.BodyTransformer

interface RequestExecutor {
    fun <T>execute(request: Request, transformer: BodyTransformer<T>, callback: CallBack<T>)
}

