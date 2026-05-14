package com.example.devara_kaadu.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "devara_kaadu_settings")

/**
 * Manages persistent user settings using Android Preferences DataStore.
 */
class SettingsManager(private val context: Context) {

    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val KANNADA_LANG_KEY = booleanPreferencesKey("kannada_lang")
        val NATURE_SOUNDS_KEY = booleanPreferencesKey("nature_sounds")
        val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
    }

    val isDarkModeFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    val isKannadaFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[KANNADA_LANG_KEY] ?: false
    }

    val natureSoundsFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NATURE_SOUNDS_KEY] ?: false
    }

    val notificationsFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[NOTIFICATIONS_KEY] ?: true
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun setKannadaLang(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KANNADA_LANG_KEY] = enabled
        }
    }

    suspend fun setNatureSounds(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NATURE_SOUNDS_KEY] = enabled
        }
    }

    suspend fun setNotifications(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_KEY] = enabled
        }
    }
}
