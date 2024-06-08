package com.example.adviceapp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adviceapp.R
import com.example.adviceapp.adapter.AdviceAdapter
import com.example.adviceapp.adapter.PreferencesManager
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class RecentAdvicesActivity : AppCompatActivity() {

    private lateinit var rvRecentAdvices: RecyclerView
    private lateinit var btnBackFromRecentAdvices: Button
    private lateinit var btnClearList: Button
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var adviceAdapter: AdviceAdapter
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_advices)

        rvRecentAdvices = findViewById(R.id.rvRecentAdvices)
        btnBackFromRecentAdvices = findViewById(R.id.btnBackFromRecentAdvices)
        btnClearList = findViewById(R.id.btnClearList)
        preferencesManager = PreferencesManager(this)

        adviceAdapter = AdviceAdapter(mutableListOf())
        rvRecentAdvices.layoutManager = LinearLayoutManager(this)
        rvRecentAdvices.adapter = adviceAdapter

        btnBackFromRecentAdvices.setOnClickListener {
            finish()
        }

        btnClearList.setOnClickListener {
            preferencesManager.clearAdviceIds()
            adviceAdapter.updateData(emptyList())
        }

        fetchAllAdvices()
    }

    private fun fetchAllAdvices() {
        val adviceIds = preferencesManager.getAdviceIds()
        if (adviceIds.isEmpty()) {
            Toast.makeText(this, "No recent advices found", Toast.LENGTH_SHORT).show()
            return
        }

        for (id in adviceIds) {
            fetchAdviceById(id)
        }
    }

    private fun fetchAdviceById(id: String) {
        val url = "https://api.adviceslip.com/advice/$id"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@RecentAdvicesActivity, "Failed to fetch advice for ID: $id", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseString = responseBody.string()
                    val json = JSONObject(responseString)
                    val slip = json.getJSONObject("slip")
                    val advice = slip.getString("advice")
                    runOnUiThread {
                        adviceAdapter.addAdvice(advice)
                    }
                }
            }
        })
    }
}