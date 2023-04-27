package ua.zp.gardendirectory.ui.menu_screen

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