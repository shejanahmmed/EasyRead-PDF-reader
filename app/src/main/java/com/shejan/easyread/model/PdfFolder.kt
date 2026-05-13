package com.shejan.easyread.model

import androidx.compose.ui.graphics.Color

data class PdfFolder(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val colorHex: Long,            // ARGB packed as Long for easy serialization
    val iconIndex: Int = 0,        // index into the icon set
    val pdfUris: List<String> = emptyList()
) {
    val color: Color get() = Color(colorHex)
}
