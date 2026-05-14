package com.example.devara_kaadu.data.repository

import com.example.devara_kaadu.data.local.UserProgressDao
import com.example.devara_kaadu.data.model.UserProgress
import com.example.devara_kaadu.data.model.Badge
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserProgressRepository(private val dao: UserProgressDao) {
    private val gson = Gson()

    fun getProgress(): Flow<UserProgress?> = dao.getProgress()

    suspend fun ensureProgressExists() = withContext(Dispatchers.IO) {
        if (dao.getProgressOnce() == null) {
            dao.upsert(UserProgress(id = 1, lastActiveDate = System.currentTimeMillis()))
        }
    }

    suspend fun incrementGrovesVisited() = withContext(Dispatchers.IO) {
        ensureProgressExists()
        dao.incrementGrovesVisited()
        checkAndAwardBadges()
    }

    suspend fun incrementSpeciesScanned() = withContext(Dispatchers.IO) {
        ensureProgressExists()
        dao.incrementSpeciesScanned()
        checkAndAwardBadges()
    }

    suspend fun incrementAlertsReported() = withContext(Dispatchers.IO) {
        ensureProgressExists()
        dao.incrementAlertsReported()
        checkAndAwardBadges()
    }

    suspend fun incrementLegendsRead() = withContext(Dispatchers.IO) {
        ensureProgressExists()
        dao.incrementLegendsRead()
    }

    /** Checks current progress and awards any newly earned badges. */
    private suspend fun checkAndAwardBadges() {
        val progress = dao.getProgressOnce() ?: return
        val earnedBadges = parseBadges(progress.badgesEarnedJson).toMutableSet()
        val originalSize = earnedBadges.size

        if (progress.grovesVisited >= 1) earnedBadges.add(Badge.GROVE_EXPLORER.name)
        if (progress.speciesScanned >= 5) earnedBadges.add(Badge.BIODIVERSITY_PROTECTOR.name)
        if (progress.alertsReported >= 3) earnedBadges.add(Badge.NATURE_GUARDIAN.name)
        if (progress.grovesVisited >= 5) earnedBadges.add(Badge.ECO_WARRIOR.name)
        if (progress.legendsRead >= 10) earnedBadges.add(Badge.LEGEND_KEEPER.name)
        if (earnedBadges.size >= Badge.entries.size - 1) earnedBadges.add(Badge.SACRED_SENTINEL.name)

        if (earnedBadges.size != originalSize) {
            withContext(Dispatchers.IO) { dao.updateBadges(gson.toJson(earnedBadges.toList())) }
        }
    }

    private fun parseBadges(json: String): List<String> {
        return try {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) { emptyList() }
    }
}
