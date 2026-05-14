package com.example.devara_kaadu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devara_kaadu.DevaraKaaduApp
import com.example.devara_kaadu.data.model.Alert
import com.example.devara_kaadu.data.model.AlertType
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AlertViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = (application as DevaraKaaduApp).alertRepository
    private val progressRepo = (application as DevaraKaaduApp).userProgressRepository

    val alerts: StateFlow<List<Alert>> = repo.getAllAlerts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _submitSuccess = MutableStateFlow(false)
    val submitSuccess: StateFlow<Boolean> = _submitSuccess

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting: StateFlow<Boolean> = _isSubmitting

    fun submitAlert(
        type: AlertType,
        description: String,
        groveId: Int = -1,
        groveName: String = "",
        latitude: Double = 0.0,
        longitude: Double = 0.0
    ) = viewModelScope.launch {
        _isSubmitting.value = true
        try {
            val alert = Alert(
                type = type.name,
                description = description,
                timestamp = System.currentTimeMillis(),
                groveId = groveId,
                groveName = groveName,
                latitude = latitude,
                longitude = longitude,
                status = "Pending"
            )
            repo.insertAlert(alert)
            progressRepo.incrementAlertsReported()
            _submitSuccess.value = true
        } finally {
            _isSubmitting.value = false
        }
    }

    fun resetSuccess() { _submitSuccess.value = false }

    fun deleteAlert(alert: Alert) = viewModelScope.launch {
        repo.deleteAlert(alert)
    }
}
