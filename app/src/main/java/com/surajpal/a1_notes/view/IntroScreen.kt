package com.surajpal.a1_notes.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.surajpal.a1_notes.MainActivity
import com.surajpal.a1_notes.R

class IntroScreen : AppCompatActivity() {

    private val SPLASH_SCREEN_DURATION = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)

        // Handler to navigate to the main activity after the splash screen duration
        Handler().postDelayed({
            // Start the main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // Finish the splash screen activity
            finish()
        }, SPLASH_SCREEN_DURATION)
    }
}