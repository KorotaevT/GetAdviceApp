package com.example.adviceapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.adviceapp.R
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var btnGetAdvice: Button
    private lateinit var btnFindAdvice: Button
    private lateinit var tvAdvice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGetAdvice = findViewById(R.id.btnGetAdvice)
        btnFindAdvice = findViewById(R.id.btnFindAdvice)
        tvAdvice = findViewById(R.id.tvAdvice)

        btnGetAdvice.setOnClickListener {
            fetchRandomAdvice()
        }

        btnFindAdvice.setOnClickListener {
            val intent = Intent(this, FindAdviceActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchRandomAdvice() {
        val url = "https://api.adviceslip.com/advice"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    tvAdvice.text = "Failed to fetch advice"
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseString = responseBody.string()
                    val json = JSONObject(responseString)
                    val slip = json.getJSONObject("slip")
                    val advice = slip.getString("advice")
                    runOnUiThread {
                        tvAdvice.text = advice
                    }
                }
            }
        })
    }
}