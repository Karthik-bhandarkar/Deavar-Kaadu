package com.example.devara_kaadu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devara_kaadu.DevaraKaaduApp
import com.example.devara_kaadu.data.model.Badge
import com.example.devara_kaadu.data.model.UserProgress
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.*

class GuardianViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = (application as DevaraKaaduApp).userProgressRepository
    private val gson = Gson()

    val progress: StateFlow<UserProgress?> = repo.getProgress()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val earnedBadges: StateFlow<List<Badge>> = progress.map { p ->
        if (p == null) return@map emptyList()
        val names = parseBadgeNames(p.badgesEarnedJson)
        Badge.entries.filter { it.name in names }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allBadges: List<Badge> = Badge.entries

    private fun parseBadgeNames(json: String): List<String> {
        return try {
            val type = object : TypeToken<List<String>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) { emptyList() }
    }
}
