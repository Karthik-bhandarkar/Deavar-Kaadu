package com.example.devara_kaadu.utils

import android.content.Context
import com.example.devara_kaadu.data.model.Grove
import com.example.devara_kaadu.data.model.Species
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Utility for loading structured JSON data from the assets directory.
 * Used to preload Room database on first launch.
 */
object JsonLoader {

    private val gson = Gson()

    /**
     * Loads grove data from assets/groves_data.json
     */
    fun loadGroves(context: Context): List<Grove> {
        return try {
            val json = context.assets.open("groves_data.json")
                .bufferedReader()
                .use { it.readText() }
            val type = object : TypeToken<List<Grove>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Loads species data from assets/species_data.json
     */
    fun loadSpecies(context: Context): List<Species> {
        return try {
            val json = context.assets.open("species_data.json")
                .bufferedReader()
                .use { it.readText() }
            val type = object : TypeToken<List<Species>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}
