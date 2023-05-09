package ua.zp.gardendirectory.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.zp.gardendirectory.data.models.MovieData
import ua.zp.gardendirectory.data.network.Api


const val NETWORK_PAGE_SIZE = 20

interface IMoviesRepository {
    suspend fun getPagedMovies(): Flow<PagingData<MovieData>>
    suspend fun getSearchedPagedMovies(query: String): Flow<PagingData<MovieData>>

}

class MoviesRepository(private val api: Api) : IMoviesRepository {
    override suspend fun getPagedMovies(): Flow<PagingData<MovieData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(apiMovie = api)
            }
        ).flow
    }

    override suspend fun getSearchedPagedMovies(query: String): Flow<PagingData<MovieData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(apiMovie = api, query)
            }
        ).flow
    }
}