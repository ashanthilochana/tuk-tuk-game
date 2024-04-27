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
//    lateinit var score: TextView
//    lateinit var highScore : TextView
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
//        score   = findViewById(R.id.score)
//        highScore = findViewById(R.id.highScore)
        mGameView = GameView(this, this)
        sharedPreferences = getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

        // Load high score from shared preferences
        val highestScore = sharedPreferences.getInt("high_score", 0)
        val score = sharedPreferences.getInt("score", 0)
        // Display the loaded high score
//        highScore.text = "High Score: $highestScore"

        mGameView.resetGame()
        mGameView.setBackgroundResource(R.drawable.game_view_road)
        rootLayout.addView(mGameView)

//        startBtn.setOnClickListener{
//            mGameView.resetGame()
//            mGameView.setBackgroundResource(R.drawable.game_view_road)
//            rootLayout.addView(mGameView)
//            startBtn.visibility = View.GONE
//            score.visibility = View.GONE
//            highScore.visibility = View.GONE
//
//        }
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

//        val highScoreUpdated = sharedPreferences.getInt("high_score", 0)


//        score.text = "Score: $mScore"
//        highScore.text = "High Score: $highScoreUpdated"

        rootLayout.removeView(mGameView)

        // Create an Intent to navigate to the GameActivity
        val intent = Intent(this, GameOverActivity::class.java)
        // Start the GameActivity
        startActivity(intent)
//        startBtn.visibility = View.VISIBLE
//        score.visibility = View.VISIBLE

//        if(highestScore != 0){
//            highScore.visibility = View.VISIBLE
//        }
//        else{
//            highScore.visibility = View.GONE
//        }
    }
}