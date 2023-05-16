package ua.zp.gardendirectory.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.zp.gardendirectory.data.models.MovieData
import ua.zp.gardendirectory.data.network.Api
import javax.inject.Inject


const val NETWORK_PAGE_SIZE = 20

interface IMoviesRepository {
    suspend fun getPagedMovies(endpoint: String): Flow<PagingData<MovieData>>
    suspend fun getSearchedPagedMovies(endpoint: String, query: String): Flow<PagingData<MovieData>>
    fun invalidate()
}

class MoviesRepository @Inject constructor(private val api: Api) : IMoviesRepository {

    private var dataSource: MoviesPagingSource? = null

    override fun invalidate() {
        dataSource?.invalidate()
    }

    override suspend fun getPagedMovies(endpoint: String): Flow<PagingData<MovieData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(apiMovie = api, "", endpoint)
                    .also { dataSource = it }
            }
        ).flow
    }

    override suspend fun getSearchedPagedMovies(
        endpoint: String,
        query: String
    ): Flow<PagingData<MovieData>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(apiMovie = api, query, endpoint)
                    .also { dataSource = it }
            }
        ).flow
    }
}