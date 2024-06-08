package com.example.adviceapp.adapter

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("advice_prefs", Context.MODE_PRIVATE)

    fun saveAdviceId(id: String) {
        val adviceIds = getAdviceIds().toMutableList()
        if (adviceIds.size >= 50) {
            adviceIds.removeAt(0) // Удаляем самый старый ID
        }
        adviceIds.add(id)
        prefs.edit().putStringSet("advice_ids", adviceIds.toSet()).apply()
    }

    fun getAdviceIds(): List<String> {
        return prefs.getStringSet("advice_ids", emptySet())?.toList() ?: emptyList()
    }

    fun clearAdviceIds() {
        prefs.edit().remove("advice_ids").apply()
    }
}