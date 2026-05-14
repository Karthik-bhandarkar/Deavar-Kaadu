package com.example.devara_kaadu.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a plant or animal species found in Sacred Groves.
 * Used by the Species Scan feature and Grove Detail screen.
 */
@Entity(tableName = "species")
data class Species(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,                   // Common English name
    val scientificName: String,         // Binomial nomenclature
    val kannadaName: String,            // Local Kannada name
    val type: String,                   // Tree, Herb, Bird, Reptile, Mammal
    val medicinalUse: String,           // Ayurvedic / folk medicine uses
    val ecologicalRole: String,         // Ecosystem role
    val sacredAssociation: String,      // Mythological / spiritual link
    val imageRes: String,               // Drawable resource name
    val conservationStatus: String,     // IUCN status
    val nativeDistricts: String,        // Comma-separated districts
    val funFact: String                 // Interesting ecological fact
)

/**
 * Type categories for species filtering.
 */
enum class SpeciesType(val displayName: String, val emoji: String) {
    ALL("All", "🌿"),
    TREE("Trees", "🌳"),
    HERB("Herbs", "🌱"),
    BIRD("Birds", "🦜"),
    REPTILE("Reptiles", "🦎"),
    MAMMAL("Mammals", "🐘"),
    INSECT("Insects", "🦋")
}
