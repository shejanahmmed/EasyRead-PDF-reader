package com.shejan.easyread.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.shejan.easyread.data.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val manager = ThemeManager(application)

    private val _theme = MutableStateFlow(manager.loadTheme())
    val theme: StateFlow<ThemePreference> = _theme.asStateFlow()

    fun setTheme(preference: ThemePreference) {
        manager.saveTheme(preference)
        _theme.value = preference
    }
}
