package ua.zp.moviedbportfolioproject.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ua.zp.moviedbportfolioproject.data.db.MovieDbEntity
import ua.zp.moviedbportfolioproject.data.db.MoviesDao
import ua.zp.moviedbportfolioproject.data.models.MovieData
import ua.zp.moviedbportfolioproject.data.network.Api
import javax.inject.Inject


const val NETWORK_PAGE_SIZE = 20

interface IMoviesRepository {
    suspend fun getPagedMovies(endpoint: String): Flow<PagingData<MovieData>>
    suspend fun getSearchedPagedMovies(endpoint: String, query: String): Flow<PagingData<MovieData>>
    fun invalidate()
}

class MoviesRepository @Inject constructor(private val api: Api, private val moviesDao: MoviesDao) :
    IMoviesRepository {

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

      fun addFavoriteMovie(movieDbEntity: MovieData) {
        TODO("Not yet implemented")

    }

     fun getFavoriteMovies(movieID: Int): Flow<MovieData> {
         return moviesDao.getFavoriteMovies(movieID).map { it.toMovieData() }
    }
}