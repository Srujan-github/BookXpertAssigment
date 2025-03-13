package labs.creative.bookxpertassigment.data.remote.api

import labs.creative.bookxpertassigment.common.Constants
import retrofit2.http.GET

fun interface UserActService {
    @GET(Constants.URL_ENDPOINT)
    suspend fun getUserActs(): String
}