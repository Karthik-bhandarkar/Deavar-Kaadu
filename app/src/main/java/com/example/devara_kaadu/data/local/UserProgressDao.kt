package com.example.devara_kaadu.data.local

import androidx.room.*
import com.example.devara_kaadu.data.model.UserProgress
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Guardian Mode progress (single-row pattern).
 */
@Dao
interface UserProgressDao {

    @Query("SELECT * FROM user_progress WHERE id = 1")
    fun getProgress(): Flow<UserProgress?>

    @Query("SELECT * FROM user_progress WHERE id = 1")
    suspend fun getProgressOnce(): UserProgress?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(progress: UserProgress)

    @Query("UPDATE user_progress SET grovesVisited = grovesVisited + 1, totalPoints = totalPoints + 50 WHERE id = 1")
    fun incrementGrovesVisited()

    @Query("UPDATE user_progress SET speciesScanned = speciesScanned + 1, totalPoints = totalPoints + 30 WHERE id = 1")
    fun incrementSpeciesScanned()

    @Query("UPDATE user_progress SET alertsReported = alertsReported + 1, totalPoints = totalPoints + 40 WHERE id = 1")
    fun incrementAlertsReported()

    @Query("UPDATE user_progress SET legendsRead = legendsRead + 1, totalPoints = totalPoints + 20 WHERE id = 1")
    fun incrementLegendsRead()

    @Query("UPDATE user_progress SET badgesEarnedJson = :badgesJson WHERE id = 1")
    fun updateBadges(badgesJson: String)
}
