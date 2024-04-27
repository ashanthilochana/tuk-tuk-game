package com.ashan.sltuktuk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class MenuActivity : AppCompatActivity(), GameTask {

    lateinit var rootLayout: ConstraintLayout
    lateinit var highScore: TextView
    lateinit var startBtn: ImageView
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        rootLayout = findViewById(R.id.rootLayout)
        highScore = findViewById(R.id.highScore)
        startBtn = findViewById(R.id.startBtn)
        sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

        // Load high score from shared preferences
        val highestScore = sharedPreferences.getInt("high_score", 0)
        // Display the loaded high score
        highScore.text = "High Score: $highestScore"

        startBtn.setOnClickListener{
            // Create an Intent to navigate to the GameActivity
            val intent = Intent(this, MainActivity::class.java)
            // Start the GameActivity
            startActivity(intent)
        }
    }
}