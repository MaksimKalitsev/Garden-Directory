package ua.zp.gardendirectory.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ua.zp.gardendirectory.data.network.responses.PlantsResponse

interface Api {

    companion object {
        private const val TOKEN = "_8KDpyHm8fnSxrTzqP8XS8pdpj7ms_xM6rSTfITt9N0"
    }

    @GET("/api/v1/plants")
    suspend fun getPlants(
        @Query("page") pageIndex: Int,
        @Query("token") token: String = TOKEN,
//        @Query("q") searchBy: String
    ): PlantsResponse

//    @GET("/api/v1/plants/search")
//    suspend fun searchPlant(
//        @Query("q") search: String,
//        @Query("token") token: String = TOKEN
//    ): PlantsResponse
}