package com.example.devara_kaadu.utils

import java.text.SimpleDateFormat
import java.util.*

/** Extension utilities used throughout the app. */

/** Format a timestamp (millis) to a readable date string. */
fun Long.toReadableDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}

/** Format meters to a human-readable distance string. */
fun Float.toDistanceString(): String = when {
    this < 1000 -> "${this.toInt()} m"
    else -> String.format("%.1f km", this / 1000f)
}

/** Clamp a float value between min and max. */
fun Float.clamp(min: Float, max: Float) = coerceIn(min, max)

/** Capitalize first letter of each word. */
fun String.titleCase(): String = split(" ").joinToString(" ") { word ->
    word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}
