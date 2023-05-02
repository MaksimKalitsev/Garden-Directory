package ua.zp.gardendirectory.repository

import ua.zp.gardendirectory.data.models.PlantData
import ua.zp.gardendirectory.data.network.Api
import ua.zp.gardendirectory.data.network.RetrofitProvider

interface IPlantsRepository{
    suspend fun getPlants() : Result<List<PlantData>>
//    suspend fun searchPlant() : Result<List<PlantData>>
}

class PlantsRepository(private val api: Api): IPlantsRepository {
    override suspend fun getPlants(): Result<List<PlantData>>  =
      try {
          val plants = api.getPlants().map { it.toPlantData() }
          Result.success(plants)
      } catch (e: Exception){
          Result.failure(e)
      }


//    override suspend fun searchPlant(): Result<List<PlantData>> {
//
//    }
}