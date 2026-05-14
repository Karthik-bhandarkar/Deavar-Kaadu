package com.example.devara_kaadu.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devara_kaadu.ui.theme.ForestGreen100

/** Reusable horizontal row of chips for species/bird lists. */
@Composable
fun ChipRow(items: List<String>, color: Color) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        items(items) { item ->
            AssistChip(
                onClick = {},
                label = { Text(item, fontSize = 12.sp) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = color.copy(alpha = 0.15f),
                    labelColor = color
                )
            )
        }
    }
}
