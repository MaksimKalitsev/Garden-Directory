package ua.zp.gardendirectory.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ua.zp.gardendirectory.data.network.responses.MovieResult

interface Api {

    companion object {
        private const val TOKEN = "0a98bb47335e64f1cc839f4708568258"
    }

    @GET("3/movie/{type}")
    suspend fun getTopRatedMovies(
        @Path("type") type: String,
        @Query("api_key") api_key: String = TOKEN,
        @Query("page") pageIndex: Int
    ): MovieResult

    @GET("3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") api_key: String = TOKEN,
        @Query("query") search: String,
        @Query("page") pageIndex: Int
    ): MovieResult
}