package com.example.adviceapp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adviceapp.adapter.AdviceAdapter
import com.example.adviceapp.R
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class FindAdviceActivity : AppCompatActivity() {

    private lateinit var etAdviceQuery: EditText
    private lateinit var btnSearchAdvice: Button
    private lateinit var rvAdviceResults: RecyclerView
    private lateinit var btnBack: Button

    private val adviceList = mutableListOf<String>()
    private val adviceAdapter = AdviceAdapter(adviceList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_advice)

        etAdviceQuery = findViewById(R.id.etAdviceQuery)
        btnSearchAdvice = findViewById(R.id.btnSearchAdvice)
        rvAdviceResults = findViewById(R.id.rvAdviceResults)
        btnBack = findViewById(R.id.btnBack)

        rvAdviceResults.layoutManager = LinearLayoutManager(this)
        rvAdviceResults.adapter = adviceAdapter

        btnSearchAdvice.setOnClickListener {
            val query = etAdviceQuery.text.toString()
            if (query.isNotEmpty()) {
                searchAdvice(query)
            } else {
                Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun searchAdvice(query: String) {
        val url = "https://api.adviceslip.com/advice/search/$query"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@FindAdviceActivity, "Failed to load advice", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseString = responseBody.string()
                    val json = JSONObject(responseString)
                    runOnUiThread {
                        if (json.has("slips")) {
                            adviceList.clear()
                            val slips = json.getJSONArray("slips")
                            for (i in 0 until slips.length()) {
                                val slip = slips.getJSONObject(i)
                                adviceList.add(slip.getString("advice"))
                            }
                            adviceAdapter.notifyDataSetChanged()
                        } else {
                            adviceList.clear()
                            adviceAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })
    }
}