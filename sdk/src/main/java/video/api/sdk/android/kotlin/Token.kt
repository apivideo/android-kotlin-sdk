package video.api.sdk.android.kotlin

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String? = null
){
    fun fetchFreshAccessToken(): String{
        return accessToken
    }
}
