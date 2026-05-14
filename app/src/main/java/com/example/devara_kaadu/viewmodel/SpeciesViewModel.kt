package com.example.devara_kaadu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devara_kaadu.DevaraKaaduApp
import com.example.devara_kaadu.data.model.Species
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SpeciesViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = (application as DevaraKaaduApp).speciesRepository

    val allSpecies: StateFlow<List<Species>> = repo.getAllSpecies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _scanResult = MutableStateFlow<Species?>(null)
    val scanResult: StateFlow<Species?> = _scanResult

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    private val _scanProgress = MutableStateFlow(0f)
    val scanProgress: StateFlow<Float> = _scanProgress

    /**
     * Simulates a local AI scan by picking a random species from the database.
     * In production, replace with actual ML model inference.
     */
    fun simulateScan() = viewModelScope.launch {
        _isScanning.value = true
        _scanResult.value = null
        _scanProgress.value = 0f

        // Animated progress simulation
        repeat(20) { i ->
            delay(120)
            _scanProgress.value = (i + 1) / 20f
        }

        // Pick random species as "identified" result
        val species = allSpecies.value
        _scanResult.value = if (species.isNotEmpty()) species.random() else null
        _isScanning.value = false

        // Award points for scanning
        getApplication<DevaraKaaduApp>().userProgressRepository.incrementSpeciesScanned()
    }

    fun clearScan() {
        _scanResult.value = null
        _scanProgress.value = 0f
    }
}
