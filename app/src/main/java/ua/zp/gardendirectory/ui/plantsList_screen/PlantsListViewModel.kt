package ua.zp.gardendirectory.ui.plantsList_screen

import androidx.lifecycle.ViewModel

class PlantsListViewModel : ViewModel() {

    var isInitialized = false
        private set

    fun init() {
        if (isInitialized.not()) {
            isInitialized = true
        }
    }
}