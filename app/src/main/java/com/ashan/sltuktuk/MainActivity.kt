package com.ashan.sltuktuk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.color.utilities.Score

class MainActivity : AppCompatActivity(), GameTask {
    lateinit var rootLayout: LinearLayout
    lateinit var mGameView: GameView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootLayout = findViewById(R.id.rootLayout)
        mGameView = GameView(this, this)
        sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

        val highestScore = sharedPreferences.getInt("high_score", 0)
        val score = sharedPreferences.getInt("score", 0)

        mGameView.resetGame()
        mGameView.setBackgroundResource(R.drawable.game_view_road)
        rootLayout.addView(mGameView)

    }

    override fun closeGame(mScore: Int) {

        val highestScore = sharedPreferences.getInt("high_score", 0)

        if (mScore > highestScore) {
            with(sharedPreferences.edit()) {
                putInt("high_score", mScore)
                apply()
            }
        }

        with(sharedPreferences.edit()){
            putInt("score", mScore)
            apply()
        }

        rootLayout.removeView(mGameView)

        val intent = Intent(this, GameOverActivity::class.java)
        startActivity(intent)
    }
}