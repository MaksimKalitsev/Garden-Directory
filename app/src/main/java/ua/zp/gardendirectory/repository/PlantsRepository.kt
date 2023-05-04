package ua.zp.gardendirectory.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.zp.gardendirectory.data.models.PlantData
import ua.zp.gardendirectory.data.network.Api
import ua.zp.gardendirectory.data.network.responses.PlantListResponse

const val NETWORK_PAGE_SIZE = 20

interface IPlantsRepository {
    suspend fun getPagedPlants(): Flow<PagingData<PlantData>>

}

class PlantsRepository(private val api: Api) : IPlantsRepository {
    override suspend fun getPagedPlants(): Flow<PagingData<PlantData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PlantsPagingSource(apiPlant = api)
            }
        ).flow
    }




}