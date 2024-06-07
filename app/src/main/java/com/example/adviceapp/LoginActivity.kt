package com.example.adviceapp

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs

class LoginActivity : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        gestureDetector = GestureDetectorCompat(this, SwipeGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { gestureDetector.onTouchEvent(it) }
        return super.onTouchEvent(event)
    }

    private inner class SwipeGestureListener : GestureDetector.SimpleOnGestureListener() {
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (e1 != null) {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (abs(diffY) > abs(diffX) && abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY < 0) {
                        onSwipeUp()
                    }
                    return true
                }
            }
            return false
        }

        private fun onSwipeUp() {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
