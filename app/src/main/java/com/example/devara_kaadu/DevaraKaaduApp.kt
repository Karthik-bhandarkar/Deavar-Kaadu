package com.example.devara_kaadu

import android.app.Application
import com.example.devara_kaadu.data.local.AppDatabase
import com.example.devara_kaadu.data.local.SettingsManager
import com.example.devara_kaadu.data.repository.*
import com.example.devara_kaadu.utils.NotificationHelper

/** Application class — sets up database and creates repository singletons. */
class DevaraKaaduApp : Application() {

    // Lazy database instance
    val database by lazy { AppDatabase.getDatabase(this) }

    // Lazy settings manager instance
    val settingsManager by lazy { SettingsManager(this) }

    // Repositories — manually provided (no DI framework for simplicity)
    val groveRepository by lazy { GroveRepository(database.groveDao()) }
    val speciesRepository by lazy { SpeciesRepository(database.speciesDao()) }
    val alertRepository by lazy { AlertRepository(database.alertDao()) }
    val userProgressRepository by lazy { UserProgressRepository(database.userProgressDao()) }

    override fun onCreate() {
        super.onCreate()
        // Schedule daily conservation reminder notifications
        NotificationHelper.createNotificationChannel(this)
        NotificationHelper.scheduleDailyReminder(this)
    }
}
