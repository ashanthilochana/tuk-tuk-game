package com.ashan.sltuktuk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class GameOverActivity : AppCompatActivity() {

    lateinit var rootLayout: ConstraintLayout
    lateinit var score: TextView
    lateinit var highScore: TextView
    lateinit var restartBtn: ImageView
    lateinit var menuBtn: ImageView
    lateinit var sharedPreferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highScore = findViewById(R.id.highScore)
        restartBtn = findViewById(R.id.restartBtn)
        menuBtn = findViewById(R.id.menuBtn)
        sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

        // Load high score from shared preferences
        val highestScore = sharedPreferences.getInt("high_score", 0)
        val userScore = sharedPreferences.getInt("score", 0)
        // Display the loaded high score
        highScore.text = "High Score: $highestScore"
        score.text = "Score: $userScore"

        restartBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        menuBtn.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}