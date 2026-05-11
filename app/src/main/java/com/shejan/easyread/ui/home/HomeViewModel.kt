package com.shejan.easyread.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shejan.easyread.data.PdfRepository
import com.shejan.easyread.data.SortOrder
import com.shejan.easyread.model.PdfFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object PermissionRequired : HomeUiState
    data class Success(
        val recentPdfs: List<PdfFile>,
        val allPdfs: List<PdfFile>,
        val searchQuery: String,
        val sortOrder: SortOrder
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var allPdfs: List<PdfFile> = emptyList()
    private var currentSort: SortOrder = SortOrder.DATE
    private var currentQuery: String = ""

    fun loadPdfs() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                allPdfs = PdfRepository.loadPdfs(getApplication(), currentSort)
                applyFilter()
            } catch (e: SecurityException) {
                _uiState.value = HomeUiState.PermissionRequired
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun onPermissionGranted() = loadPdfs()

    fun onPermissionDenied() {
        _uiState.value = HomeUiState.PermissionRequired
    }

    fun onSearchQueryChange(query: String) {
        currentQuery = query
        applyFilter()
    }

    fun onSortChange(sort: SortOrder) {
        currentSort = sort
        viewModelScope.launch {
            allPdfs = PdfRepository.loadPdfs(getApplication(), currentSort)
            applyFilter()
        }
    }

    private fun applyFilter() {
        val filtered = if (currentQuery.isBlank()) {
            allPdfs
        } else {
            allPdfs.filter { it.name.contains(currentQuery, ignoreCase = true) }
        }
        // Recent = first 8 by date (already sorted desc by date in repository on DATE sort)
        val recent = allPdfs.sortedByDescending { it.lastModified }.take(8)
        _uiState.value = HomeUiState.Success(
            recentPdfs = recent,
            allPdfs    = filtered,
            searchQuery = currentQuery,
            sortOrder  = currentSort
        )
    }
}
