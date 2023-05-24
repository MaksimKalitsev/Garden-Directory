package ua.zp.moviedbportfolioproject.ui.moviesList_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.zp.moviedbportfolioproject.MovieType
import ua.zp.moviedbportfolioproject.data.models.MovieData
import ua.zp.moviedbportfolioproject.repository.IMoviesRepository
import ua.zp.moviedbportfolioproject.utill.OneTimeEvent
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: IMoviesRepository
) : ViewModel() {

    lateinit var moviesFlow: Flow<PagingData<MovieData>>
    private val searchBy: MutableLiveData<OneTimeEvent<String>> = MutableLiveData(OneTimeEvent(""))
    private lateinit var tabChanged: MutableLiveData<OneTimeEvent<MovieType>>
    private lateinit var typeMovie: MovieType
    private var isInitialized = false

    fun init(type: MovieType) {
        if (isInitialized.not()) {
            typeMovie = type
            setupFlows(type)
            isInitialized = true
        } else {
            tabChanged.value = OneTimeEvent(typeMovie)
        }
    }

    private fun setupFlows(type: MovieType) {
        tabChanged = MutableLiveData(OneTimeEvent(type))
        moviesFlow = merge(getMovieTypeFlow(), getSearchFlow())
    }

    private fun getMovieTypeFlow(): Flow<PagingData<MovieData>> =
        tabChanged
            .asFlow()
            .flatMapLatest {
                requestByType(it.getPayload()!!)
            }
            .cachedIn(viewModelScope)

    private fun getSearchFlow(): Flow<PagingData<MovieData>> =
        searchBy.asFlow()
            .debounce(500)
            .flatMapLatest {
                requestByQuery(it.getPayload()!!)
            }
            .cachedIn(viewModelScope)

    private suspend fun requestByType(movieType: MovieType) = withContext(Dispatchers.IO) {
        if (movieType==MovieType.FAVORITE){
            repository.getFavoriteMovies()
        }else
        repository.getPagedMovies(movieType.endpoint)

    }

    private suspend fun requestByQuery(query: String) = withContext(Dispatchers.IO) {
        repository.getSearchedPagedMovies(typeMovie.endpoint, query)
    }

    fun setSearchBy(value: String, isRefresh: Boolean = false) {
        if (this.searchBy.value?.getPayload() == value && isRefresh.not()) return
        this.searchBy.value = OneTimeEvent(value)
    }

    fun setMovieType(movieType: MovieType, isRefresh: Boolean = false) {
        if (tabChanged.value?.getPayload() == movieType && isRefresh.not()) return
        typeMovie = movieType
        tabChanged.value = OneTimeEvent(movieType)
    }

    fun refresh() {
        repository.invalidate()
        repository.invalidateDb()
    }

    fun addFavoriteMovie(movieData: MovieData) {
        viewModelScope.launch {
            repository.addFavoriteMovie(movieData)
        }
    }
    fun deleteFavoriteMovie(movieData: MovieData){
        viewModelScope.launch {
            repository.deleteFavoriteMovie(movieData)
            repository.invalidateDb()
        }
    }

}