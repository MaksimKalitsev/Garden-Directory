package ua.zp.gardendirectory.ui.plantsList_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ua.zp.gardendirectory.data.models.PlantData
import ua.zp.gardendirectory.data.network.RetrofitProvider
import ua.zp.gardendirectory.data.network.responses.PlantListResponse
import ua.zp.gardendirectory.repository.PlantsRepository
import ua.zp.gardendirectory.ui.details_screen.PlantsListState

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class PlantsListViewModel : ViewModel() {

//    val liveData = MutableLiveData(PlantsListState(RequestState.LOADING))
    private var repository = PlantsRepository(RetrofitProvider.api)
    val plantsFlow: Flow<PagingData<PlantData>>
    private val searchBy = MutableLiveData("")


    var isInitialized = false
        private set

    fun init() {
        if (isInitialized.not()) {
            isInitialized = true
        }
    }
    init {
        plantsFlow = searchBy.asFlow()
            .debounce(500)
            .flatMapLatest {
                withContext(Dispatchers.IO) {
                    repository.getPagedPlants()
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

//    private val currentState: PlantsListState
//        get() = liveData.value!!

//    fun getPlants() {
//        viewModelScope.launch {
//            liveData.value = currentState.copy(requestState = RequestState.LOADING)
//            delay(1000)
//            val result = withContext(Dispatchers.IO) {
//                repository.getPlants()
//            }
//            result.onSuccess {
//                liveData.value = currentState.copy(data = it, requestState = RequestState.SUCCESS)
//            }.onFailure {
//                liveData.value = currentState.copy(requestState = RequestState.ERROR)
//            }
//        }
//    }


}