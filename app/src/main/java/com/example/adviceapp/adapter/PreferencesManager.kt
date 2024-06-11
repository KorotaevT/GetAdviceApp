package com.example.adviceapp.adapter

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("advice_prefs", Context.MODE_PRIVATE)

    fun saveAdviceId(id: String) {
        val adviceIds = getAdviceIds().toMutableList()
        adviceIds.add(id)
        saveAdviceIds(adviceIds)
    }

    fun getAdviceIds(): List<String> {
        val adviceIdsJson = prefs.getString("advice_ids", "[]")
        val jsonArray = JSONArray(adviceIdsJson)
        val adviceIds = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            adviceIds.add(jsonArray.getString(i))
        }
        return adviceIds
    }

    private fun saveAdviceIds(adviceIds: List<String>) {
        val jsonArray = JSONArray(adviceIds)
        prefs.edit().putString("advice_ids", jsonArray.toString()).apply()
    }

    fun clearAdviceIds() {
        prefs.edit().remove("advice_ids").apply()
    }
}