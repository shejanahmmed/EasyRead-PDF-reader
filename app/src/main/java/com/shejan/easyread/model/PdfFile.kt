package com.shejan.easyread.model

data class PdfFile(
    val id: Long,
    val name: String,           // display name without extension
    val rawName: String,        // full filename e.g. "report.pdf"
    val path: String,           // absolute file path
    val uri: String,            // content:// URI string
    val sizeBytes: Long,
    val lastModified: Long      // epoch millis
) {
    /** Human-readable size string, e.g. "3.2 MB" */
    val sizeLabel: String get() {
        val kb = sizeBytes / 1024.0
        val mb = kb / 1024.0
        return when {
            mb >= 1.0 -> "%.1f MB".format(mb)
            kb >= 1.0 -> "%.0f KB".format(kb)
            else      -> "$sizeBytes B"
        }
    }
}
