package video.api.androidkotlinsdk.http

import okhttp3.*
import org.json.JSONObject
import video.api.androidkotlinsdk.CallBack
import video.api.androidkotlinsdk.model.BodyTransformer
import video.api.androidkotlinsdk.model.Error
import java.io.IOException

class HttpRequestExecutor(
    private val client: OkHttpClient
) : RequestExecutor {

    override fun <T> execute(
        request: Request,
        transformer: BodyTransformer<T>,
        callback: CallBack<T>
    ) {

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFatal(e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    when (response.code) {
                        200, 201 -> {
                            val body = JSONObject(response.body!!.string())
                            callback.onSuccess(transformer.fromJson(body))
                        }
                        204 -> {
                            callback.onSuccess(transformer.fromJson(JSONObject()))
                        }
                        400, 404 -> {
                            val body = JSONObject(response.body!!.string())
                            callback.onError(Error(body))
                        }
                        else -> {
                            callback.onFatal(IOException("Unexpected code $response"))
                        }
                    }
                }
            }
        })
    }
}