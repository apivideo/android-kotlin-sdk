package video.api.androidkotlinsdk

data class Token(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String? = null
){
    fun fetchFreshAccessToken(): String{
        return accessToken
    }
}
