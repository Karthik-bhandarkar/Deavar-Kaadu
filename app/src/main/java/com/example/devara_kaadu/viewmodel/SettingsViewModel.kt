package com.example.devara_kaadu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devara_kaadu.DevaraKaaduApp
import com.example.devara_kaadu.data.local.SettingsManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel managing user preferences via SettingsManager DataStore streams.
 */
class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val manager: SettingsManager = (application as DevaraKaaduApp).settingsManager

    val isDarkMode: StateFlow<Boolean> = manager.isDarkModeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isKannada: StateFlow<Boolean> = manager.isKannadaFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val natureSounds: StateFlow<Boolean> = manager.natureSoundsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val notifications: StateFlow<Boolean> = manager.notificationsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setDarkMode(enabled: Boolean) = viewModelScope.launch {
        manager.setDarkMode(enabled)
    }

    fun setKannada(enabled: Boolean) = viewModelScope.launch {
        manager.setKannadaLang(enabled)
    }

    fun setNatureSounds(enabled: Boolean) = viewModelScope.launch {
        manager.setNatureSounds(enabled)
    }

    fun setNotifications(enabled: Boolean) = viewModelScope.launch {
        manager.setNotifications(enabled)
    }
}
