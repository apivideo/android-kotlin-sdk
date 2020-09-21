package video.api.sdk.android.kotlin

import android.util.Log
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class TokenAuthenticator(private val tokenHolder: TokenHolder,
                         private val baseUri: String,
                         private val apiKey: String
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        Log.e("response", response.code.toString())
        if (response.request.header("Authorization") != null) {
            return null
        }
        val token = tokenHolder.token
        if (token == null) {
            tokenHolder.token = requestAccessToken(JSONObject().put("apiKey", apiKey))
            println("access token requested")
        }else{
            tokenHolder.token = requestAccessToken(JSONObject().put("refreshToken", token.refreshToken))

        }
        return response.request.addAuthorization(tokenHolder.token)
    }

    private fun requestAccessToken(body: JSONObject): Token {
        val request = Request.Builder()
            .url("$baseUri/auth/api-key")
            .post(body.toString().toRequestBody())
            .build()

        val response = OkHttpClient().newCall(request).execute()

        if (response.code != 200) {
            throw Exception(response.body!!.string())
        }

        val jsonToken = JSONObject(response.body!!.string());
        return Token(jsonToken.getString("access_token"), jsonToken.getString("refresh_token"), jsonToken.getString("token_type"))
    }

}

fun Request.addAuthorization(token: Token?) =
    if (token == null) this
    else newBuilder()
        .header("Authorization", "Bearer ${token.accessToken}")
        .build()

