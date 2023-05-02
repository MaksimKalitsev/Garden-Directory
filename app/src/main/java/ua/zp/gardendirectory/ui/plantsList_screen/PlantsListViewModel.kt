package ua.zp.gardendirectory.ui.plantsList_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.zp.gardendirectory.data.network.RetrofitProvider
import ua.zp.gardendirectory.repository.IPlantsRepository
import ua.zp.gardendirectory.repository.PlantsRepository
import ua.zp.gardendirectory.ui.details_screen.PlantsListState

class PlantsListViewModel : ViewModel() {

     val liveData = MutableLiveData<PlantsListState>()
    private var repository = PlantsRepository(RetrofitProvider.api)

    var isInitialized = false
        private set

    fun init() {
        if (isInitialized.not()) {
            isInitialized = true
        }
    }
    private val currentState: PlantsListState
        get() = liveData.value!!
    fun getPlants(){
        viewModelScope.launch {
            liveData.value = currentState.copy(requestState = RequestState.LOADING)
            delay(1000)
            val result = withContext(Dispatchers.IO){
                repository.getPlants()
            }
            result.onSuccess {
                liveData.value = currentState.copy(data = it, requestState = RequestState.SUCCESS)
            }.onFailure {
                liveData.value = currentState.copy(requestState = RequestState.ERROR)
            }
        }
    }
}