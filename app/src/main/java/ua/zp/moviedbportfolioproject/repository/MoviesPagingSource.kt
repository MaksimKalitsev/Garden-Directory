package ua.zp.moviedbportfolioproject.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.zp.moviedbportfolioproject.data.models.MovieData
import ua.zp.moviedbportfolioproject.data.network.Api


private const val STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val apiMovie: Api,
    private val query: String = "",
    private val endpoint: String,
    private val movieStateFetcher: IMoviesStateFetcher
) : PagingSource<Int, MovieData>() {

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        val refreshKey = page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
        println(refreshKey)
        return refreshKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = if (query.isEmpty()) {
                apiMovie.getListMovies(
                    type = endpoint,
                    pageIndex = pageIndex
                )
            } else {
                apiMovie.searchMovie(
                    search = query,
                    pageIndex = pageIndex
                )
            }
            val movies = response.results.map { it.toMovieData() }

            val moviesStatesMap = withContext(Dispatchers.IO){
                movieStateFetcher.getStateMap(movies.map { it.id })
            }
            val resultingMovies = movies.map { it.copy(isFavorite = moviesStatesMap[it.id] ?: false) }
            val nextKey =
                if (resultingMovies.isEmpty() || resultingMovies.size % 20 != 0) null
                else pageIndex + 1
            LoadResult.Page(
                data = resultingMovies,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            LoadResult.Error(ex)
        }
    }
}