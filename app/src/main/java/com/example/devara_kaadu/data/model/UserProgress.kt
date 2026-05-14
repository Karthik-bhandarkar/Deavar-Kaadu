package com.example.devara_kaadu.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tracks user's Guardian Mode progress, badges, and activity.
 * Single-row table (id = 1 always).
 */
@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey
    val id: Int = 1,
    val grovesVisited: Int = 0,
    val speciesScanned: Int = 0,
    val alertsReported: Int = 0,
    val legendsRead: Int = 0,
    val totalPoints: Int = 0,
    val badgesEarnedJson: String = "[]",    // JSON array of BadgeType names
    val lastActiveDate: Long = 0L,
    val streakDays: Int = 0
)

/**
 * Guardian badge definitions with unlock requirements.
 */
enum class Badge(
    val title: String,
    val description: String,
    val icon: String,
    val requiredPoints: Int
) {
    GROVE_EXPLORER(
        "Sacred Grove Explorer",
        "Visited your first Sacred Grove",
        "🌿",
        50
    ),
    BIODIVERSITY_PROTECTOR(
        "Biodiversity Protector",
        "Scanned 5 different species",
        "🦋",
        150
    ),
    NATURE_GUARDIAN(
        "Nature Guardian",
        "Reported 3 conservation alerts",
        "🛡️",
        200
    ),
    ECO_WARRIOR(
        "Eco Warrior",
        "Visited 5 sacred groves",
        "⚔️",
        300
    ),
    LEGEND_KEEPER(
        "Legend Keeper",
        "Read 10 sacred legends",
        "📜",
        250
    ),
    SACRED_SENTINEL(
        "Sacred Sentinel",
        "Earned all other badges",
        "🌟",
        1000
    )
}
