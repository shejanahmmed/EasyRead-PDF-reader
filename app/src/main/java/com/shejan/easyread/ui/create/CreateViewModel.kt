package com.shejan.easyread.ui.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.shejan.easyread.model.PdfFolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateViewModel(application: Application) : AndroidViewModel(application) {

    private val _folders = MutableStateFlow<List<PdfFolder>>(emptyList())
    val folders: StateFlow<List<PdfFolder>> = _folders.asStateFlow()

    fun addFolder(folder: PdfFolder) {
        _folders.value = _folders.value + folder
    }

    fun deleteFolder(folderId: Long) {
        _folders.value = _folders.value.filter { it.id != folderId }
    }
}
