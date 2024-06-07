package com.example.adviceapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var tvAdvice: TextView
    private lateinit var btnGetAdvice: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvAdvice = findViewById(R.id.tvAdvice)
        btnGetAdvice = findViewById(R.id.btnGetAdvice)

        btnGetAdvice.setOnClickListener {
            getRandomAdvice()
        }
    }

    private fun getRandomAdvice() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.adviceslip.com/advice")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val json = JSONObject(it)
                    val advice = json.getJSONObject("slip").getString("advice")
                    runOnUiThread {
                        tvAdvice.text = advice
                    }
                }
            }
        })
    }
}
