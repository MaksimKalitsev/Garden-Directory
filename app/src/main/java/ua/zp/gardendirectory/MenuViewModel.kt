package ua.zp.gardendirectory

import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {

    var isInitialized = false
        private set

    fun init() {
        if (isInitialized.not()) {
            isInitialized = true
        }
    }
}