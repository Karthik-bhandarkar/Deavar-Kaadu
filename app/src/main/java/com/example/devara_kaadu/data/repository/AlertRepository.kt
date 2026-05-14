package com.example.devara_kaadu.data.repository

import com.example.devara_kaadu.data.local.AlertDao
import com.example.devara_kaadu.data.model.Alert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlertRepository(private val alertDao: AlertDao) {
    fun getAllAlerts(): Flow<List<Alert>> = alertDao.getAllAlerts()
    fun getAlertsByType(type: String): Flow<List<Alert>> = alertDao.getAlertsByType(type)
    suspend fun insertAlert(alert: Alert) = withContext(Dispatchers.IO) { alertDao.insert(alert) }
    suspend fun deleteAlert(alert: Alert) = withContext(Dispatchers.IO) { alertDao.delete(alert) }
    suspend fun updateStatus(id: Int, status: String) = withContext(Dispatchers.IO) { alertDao.updateStatus(id, status) }
    suspend fun getAlertCount(): Int = alertDao.getAlertCount()
}
