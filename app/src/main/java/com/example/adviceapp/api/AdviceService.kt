package com.example.adviceapp.api

import android.os.StrictMode
import android.util.Log
import com.example.adviceapp.entity.AdviceResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class AdviceService {

    private val client = OkHttpClient()

    fun getAdvice(): String? {
        return runCatching {

            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)

            val request = Request.Builder()
                .url(ADVICE_API_URL)
                .get()
                .build()

            Log.d("AdviceService", "Request: $request")

            client.newCall(request).execute().use { response ->
                Log.d("AdviceService", "Response code: ${response.code}")
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                val responseBody = response.body?.string()
                Log.d("AdviceService", "Response body: $responseBody")
                val adviceResponse = Gson().fromJson(responseBody, AdviceResponse::class.java)
                adviceResponse.slip.advice
            }
        }.onFailure {
            Log.e("AdviceService", "Error fetching advice", it)
        }.getOrNull()
    }
}
