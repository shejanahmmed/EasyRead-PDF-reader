package com.shejan.easyread.data

import android.content.Context
import com.shejan.easyread.ui.theme.ThemePreference

class ThemeManager(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveTheme(theme: ThemePreference) {
        prefs.edit().putString(KEY_THEME, theme.name).apply()
    }

    fun loadTheme(): ThemePreference {
        val saved = prefs.getString(KEY_THEME, ThemePreference.SYSTEM.name)
        return ThemePreference.entries.firstOrNull { it.name == saved }
            ?: ThemePreference.SYSTEM
    }

    companion object {
        private const val PREFS_NAME = "easyread_prefs"
        private const val KEY_THEME  = "theme"
    }
}
