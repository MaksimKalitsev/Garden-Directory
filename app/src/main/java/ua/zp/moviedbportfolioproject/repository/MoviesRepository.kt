package ua.zp.moviedbportfolioproject.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ua.zp.moviedbportfolioproject.data.db.MovieDbEntity
import ua.zp.moviedbportfolioproject.data.db.MoviesDao
import ua.zp.moviedbportfolioproject.data.models.MovieData
import ua.zp.moviedbportfolioproject.data.network.Api
import javax.inject.Inject


const val NETWORK_PAGE_SIZE = 20
const val DB_PAGE_SIZE = 20

interface IMoviesRepository {
    suspend fun getPagedMovies(endpoint: String): Flow<PagingData<MovieData>>
    suspend fun getSearchedPagedMovies(endpoint: String, query: String): Flow<PagingData<MovieData>>
    fun invalidate()
    fun addFavoriteMovie(movieData: MovieData)
    fun getFavoriteMovies(): Flow<PagingData<MovieData>>
}

class MoviesRepository @Inject constructor(private val api: Api, private val moviesDao: MoviesDao) :
    IMoviesRepository {

    private var dataSource: MoviesPagingSource? = null
    private var dataSourceDb: MoviesDbPagingSource? = null

    private val moviesStateFetcher = object : IMoviesStateFetcher {
        override suspend fun getStateMap(ids: List<Int>): Map<Int, Boolean> {
            val stateMap = mutableMapOf<Int, Boolean>()
            val movies = moviesDao.getMoviesByIds(ids)
            for (movie in movies) {
                stateMap[movie.id] = movie.isFavorite
            }
            return stateMap
        }
    }

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
                MoviesPagingSource(apiMovie = api, "", endpoint, moviesStateFetcher)
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
                MoviesPagingSource(apiMovie = api, query, endpoint, moviesStateFetcher)
                    .also { dataSource = it }
            }
        ).flow
    }

    override fun addFavoriteMovie(movieData: MovieData) {
        CoroutineScope(Dispatchers.IO).launch {
            moviesDao.addFavoriteMovie(movieData.toDbEntity())
        }
    }

    override fun getFavoriteMovies(): Flow<PagingData<MovieData>> {
        return Pager(
            config = PagingConfig(
                pageSize = DB_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesDbPagingSource(moviesDao)
                    .also { dataSourceDb = it }
            }
        ).flow
//        return moviesDao.getFavoriteMovies().map { it.toMovieData() }
    }
}