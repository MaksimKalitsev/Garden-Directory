package ua.zp.gardendirectory.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ua.zp.gardendirectory.data.network.responses.PlantListResponse

interface Api {

    @GET("/api/v1/plants")
    fun getPlants(
        @Query("token") token: String,
        ): List<PlantListResponse>

    @GET("/api/v1/plants/search")
    fun searchPlant(
        @Query("token") token: String,
        @Query("q") search: String
    )
}