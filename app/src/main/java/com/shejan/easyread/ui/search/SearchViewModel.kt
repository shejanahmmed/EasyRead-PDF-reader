package com.shejan.easyread.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shejan.easyread.data.PdfRepository
import com.shejan.easyread.model.PdfFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _allPdfs = MutableStateFlow<List<PdfFile>>(emptyList())
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    val filteredPdfs: StateFlow<List<PdfFile>> = combine(_searchQuery, _allPdfs) { query, pdfs ->
        if (query.isBlank()) {
            emptyList()
        } else {
            pdfs.filter { it.name.contains(query, ignoreCase = true) }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadPdfs()
    }

    private fun loadPdfs() {
        viewModelScope.launch {
            _isLoading.value = true
            val pdfs = PdfRepository.loadPdfs(getApplication())
            _allPdfs.value = pdfs
            _isLoading.value = false
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}
