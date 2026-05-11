package com.shejan.easyread.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.shejan.easyread.model.PdfFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

enum class SortOrder { NAME, DATE, SIZE }

object PdfRepository {

    suspend fun loadPdfs(context: Context, sortOrder: SortOrder = SortOrder.DATE): List<PdfFile> =
        withContext(Dispatchers.IO) {
            val results = mutableListOf<PdfFile>()

            val collection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)

            val projection = arrayOf(
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DISPLAY_NAME,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.MIME_TYPE
            )

            val selection = "${MediaStore.Files.FileColumns.MIME_TYPE} = ?"
            val selectionArgs = arrayOf("application/pdf")

            val sortColumn = when (sortOrder) {
                SortOrder.NAME -> "${MediaStore.Files.FileColumns.DISPLAY_NAME} ASC"
                SortOrder.DATE -> "${MediaStore.Files.FileColumns.DATE_MODIFIED} DESC"
                SortOrder.SIZE -> "${MediaStore.Files.FileColumns.SIZE} DESC"
            }

            try {
                context.contentResolver.query(
                    collection, projection, selection, selectionArgs, sortColumn
                )?.use { cursor ->
                    val idCol       = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
                    val nameCol     = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                    val dataCol     = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                    val sizeCol     = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
                    val modifiedCol = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED)

                    while (cursor.moveToNext()) {
                        val id       = cursor.getLong(idCol)
                        val rawName  = cursor.getString(nameCol) ?: continue
                        val path     = cursor.getString(dataCol) ?: continue
                        val size     = cursor.getLong(sizeCol)
                        val modified = cursor.getLong(modifiedCol) * 1000L // to millis

                        val contentUri: Uri = ContentUris.withAppendedId(collection, id)
                        val displayName = rawName.removeSuffix(".pdf").removeSuffix(".PDF")

                        results.add(
                            PdfFile(
                                id           = id,
                                name         = displayName,
                                rawName      = rawName,
                                path         = path,
                                uri          = contentUri.toString(),
                                sizeBytes    = size,
                                lastModified = modified
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            results
        }
}
