package video.api.sdk.android.kotlin.http

import okhttp3.Request
import video.api.sdk.android.kotlin.CallBack
import video.api.sdk.android.kotlin.model.BodyTransformer

interface RequestExecutor {
    fun <T>execute(request: Request, transformer: BodyTransformer<T>, callback: CallBack<T>)
}

