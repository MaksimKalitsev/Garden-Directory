package ua.zp.gardendirectory.ui.details_screen

import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {
    var isInitialized = false
        private set

    fun init() {
        if (isInitialized.not()) {
            isInitialized = true
        }
    }
}