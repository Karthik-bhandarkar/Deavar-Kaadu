package com.example.devara_kaadu.data.local

import androidx.room.*
import com.example.devara_kaadu.data.model.Alert
import kotlinx.coroutines.flow.Flow

/**
 * DAO for conservation alert reports — fully offline storage.
 */
@Dao
interface AlertDao {

    @Query("SELECT * FROM alerts ORDER BY timestamp DESC")
    fun getAllAlerts(): Flow<List<Alert>>

    @Query("SELECT * FROM alerts WHERE id = :id")
    suspend fun getAlertById(id: Int): Alert?

    @Query("SELECT * FROM alerts WHERE type = :type ORDER BY timestamp DESC")
    fun getAlertsByType(type: String): Flow<List<Alert>>

    @Query("SELECT COUNT(*) FROM alerts")
    suspend fun getAlertCount(): Int

    @Query("UPDATE alerts SET status = :status WHERE id = :id")
    fun updateStatus(id: Int, status: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alert: Alert)

    @Delete
    fun delete(alert: Alert)
}
