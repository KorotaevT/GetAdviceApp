package com.example.adviceapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.adviceapp.api.AdviceService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adviceService = AdviceService()
        val getAdviceButton: Button = findViewById(R.id.getAdviceButton)
        val adviceTextView: TextView = findViewById(R.id.adviceTextView)

        getAdviceButton.setOnClickListener {
            val advice = adviceService.getAdvice()
            adviceTextView.text = advice
        }
    }
}
