package com.example.devara_kaadu.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a Sacred Grove entry in the database.
 * Maps directly to the groves_data.json structure.
 */
@Entity(tableName = "groves")
data class Grove(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val village: String,
    val district: String,
    val type: String,               // Kaavu, Bana, Nagabana, Devarakadu, Kans
    val deity: String,
    val biodiversityInfo: String,
    val scientificInfo: String,
    val climateRole: String,
    val waterRole: String,
    val imageRes: String,           // drawable resource name string
    val latitude: Double,
    val longitude: Double,
    val legendStory: String,
    val traditions: String,
    val ritualPractices: String,
    val nativeSpeciesJson: String,  // JSON array of species names
    val birdSpeciesJson: String,    // JSON array of bird names
    val waterSources: String,
    val ecologicalImportance: String,
    val rareMedicinalPlants: String,
    val isVisited: Boolean = false,
    val areaHectares: Double = 0.0,
    val treeCount: Int = 0
)

/**
 * Filter/display types for the grove directory.
 */
enum class GroveType(val displayName: String, val kannadaName: String) {
    ALL("All", "ಎಲ್ಲಾ"),
    KAAVU("Kaavu", "ಕಾವು"),
    BANA("Bana", "ಬನ"),
    NAGABANA("Nagabana", "ನಾಗಬನ"),
    DEVARAKADU("Devarakadu", "ದೇವರಕಾಡು"),
    KANS("Kans Forest", "ಕಾನ್ಸ")
}
