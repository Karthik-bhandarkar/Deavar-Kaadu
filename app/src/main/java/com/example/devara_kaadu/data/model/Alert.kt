package com.example.devara_kaadu.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Conservation alert / issue report filed by the user.
 * Stored entirely offline in Room.
 */
@Entity(tableName = "alerts")
data class Alert(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,               // AlertType enum name
    val description: String,
    val imagePath: String = "",     // Local file path
    val timestamp: Long,            // System.currentTimeMillis()
    val groveId: Int = -1,          // -1 if not grove-specific
    val groveName: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val status: String = "Pending"  // Pending, Acknowledged, Resolved
)

/**
 * Categories of conservation issues users can report.
 */
enum class AlertType(val displayName: String, val icon: String, val color: Long) {
    TREE_CUTTING("Tree Cutting", "🪓", 0xFFD62828),
    WASTE_DUMPING("Waste Dumping", "🗑️", 0xFFF77F00),
    FIRE_RISK("Fire Risk", "🔥", 0xFFE63946),
    ENCROACHMENT("Encroachment", "⚠️", 0xFFFCAB10),
    POACHING("Poaching", "🚨", 0xFF9B2335),
    WATER_POLLUTION("Water Pollution", "💧", 0xFF1D3557),
    OTHER("Other Issue", "❓", 0xFF6B7B6E)
}
