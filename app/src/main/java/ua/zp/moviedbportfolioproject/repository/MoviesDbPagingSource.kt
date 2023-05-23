package ua.zp.moviedbportfolioproject.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.zp.moviedbportfolioproject.data.db.MoviesDao
import ua.zp.moviedbportfolioproject.data.models.MovieData


class MoviesDbPagingSource(private val moviesDao: MoviesDao) : PagingSource<Int, MovieData>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        val refreshKey = page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
        println(refreshKey)
        return refreshKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        val position = params.key ?: STARTING_PAGE_INDEX
        val responseDb = withContext(Dispatchers.IO) {
            moviesDao.getFavoriteMovies(params.loadSize, position*params.loadSize).map { it.toMovieData() }
        }
        return LoadResult.Page(
            data = responseDb,
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = if (responseDb.isEmpty()) null else position + 1
        )
    }
}