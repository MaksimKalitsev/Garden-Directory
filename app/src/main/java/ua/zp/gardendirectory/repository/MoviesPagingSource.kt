package ua.zp.gardendirectory.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ua.zp.gardendirectory.data.models.MovieData
import ua.zp.gardendirectory.data.network.Api
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val apiMovie: Api,
    private val query: String = "",
    private val endpoint: String
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
                apiMovie.getTopRatedMovies(
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
            val nextKey =
                if (movies.isEmpty() || movies.size % 20 != 0) null
                else pageIndex + 1
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (ex: Exception) {
            LoadResult.Error(ex)
        }
    }
}