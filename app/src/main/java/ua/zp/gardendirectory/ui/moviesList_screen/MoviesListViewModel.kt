package ua.zp.gardendirectory.ui.moviesList_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ua.zp.gardendirectory.MovieType
import ua.zp.gardendirectory.data.models.MovieData
import ua.zp.gardendirectory.data.network.RetrofitProvider
import ua.zp.gardendirectory.repository.MoviesRepository

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MoviesListViewModel : ViewModel() {

    private var repository = MoviesRepository(RetrofitProvider.api)
    lateinit var moviesFlow: Flow<PagingData<MovieData>>
    private val searchBy = MutableLiveData("")

    private lateinit var typeMovie: MovieType

    var isInitialized = false
        private set

    fun init(type: MovieType) {
        if (isInitialized.not()) {
            typeMovie = type
            initFlow()
            isInitialized = true
        }
    }

    private fun initFlow() {
        moviesFlow = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest {
                if (it.isNullOrEmpty()) {
                    withContext(Dispatchers.IO) {
                        repository.getPagedMovies(typeMovie.endpoint)
                    }
                } else {
                    repository.getSearchedPagedMovies(it)
                }
            }
            .cachedIn(viewModelScope)
    }

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
    }

    fun refresh() {
        this.searchBy.postValue(this.searchBy.value)

    }
}