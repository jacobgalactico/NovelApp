package com.example.novelapp



import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_THEME = "theme"
    }

    // Guardar tema
    fun setDarkModeEnabled(enabled: Boolean) {
        preferences.edit().putBoolean(KEY_THEME, enabled).apply()
    }

    // Leer tema
    fun isDarkModeEnabled(): Boolean {
        return preferences.getBoolean(KEY_THEME, false)
    }
}
