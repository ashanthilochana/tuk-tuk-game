package com.ashan.sltuktuk

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import android.animation.ValueAnimator

class GameView (var c: Context, var gameTask: GameTask): View(c)
{
    private var myPaint: Paint? = null
    private var speed = 1
    private var time = 0
    private var score = 0
    private var highestScore = 0
    private var myCarPosition = 0
    private val otherCars = ArrayList<HashMap<String, Any>>()
    private var sharedPreferences = c.getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

    private var carAnimator: ObjectAnimator? = null
    private var highScoreTextStatus = false
    private var highScoreTextTimeCount = 0


    var viewWidth = 0
    var viewHeigh = 0
    init {
        myPaint = Paint()

        // Load high score from shared preferences
        highestScore = sharedPreferences.getInt("high_score", 0)
    }

    // Reset the game state
    fun resetGame() {
        // Clear all existing enemy cars
        otherCars.clear()

        // Reset game variables
        speed = 1
        time = 0
        score = 0
        myCarPosition = 0
        highScoreTextStatus = false
        highScoreTextTimeCount = 0


        // Restart the game loop
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeigh = this.measuredHeight

        if(time % 700 < 10 + speed){
            val map = HashMap<String, Any>()
            map["lane"] = (0..3).random()
            map["startTime"] = time
            otherCars.add(map)
        }

        time = time + 10 + speed
        val carWidth = viewWidth / 5
        val carHeight = carWidth + 10

        myPaint!!.style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.tuk_tuk_character, null)

        d.setBounds(
            myCarPosition * viewWidth / 4 + viewWidth / 40 + 25,
            viewHeigh - 2 - carHeight,
            myCarPosition * viewWidth / 4 + viewWidth / 40 + carWidth - 25,
            viewHeigh - 2
        )
        d.draw(canvas!!)
        myPaint!!.color = Color.GREEN
        var highScore = 0

        val iterator = otherCars.iterator()
        while (iterator.hasNext()) {
            val car = iterator.next()
            try {
                val carX = car["lane"] as Int * viewWidth / 4 + viewWidth / 40
                var carY = time - car["startTime"] as Int
                var d2 = resources.getDrawable(R.drawable.car_yellow, null)

                d2.setBounds(
                    carX + 25, carY - carHeight, carX + carWidth - 25, carY
                )

                d2.draw(canvas)
                if (car["lane"] as Int == myCarPosition) {
                    if (carY > viewHeigh - 2 - carHeight && carY < viewHeigh - 2) {
                        gameTask.closeGame(score)
                    }
                }
                if (carY > viewHeigh + carHeight) {
                    iterator.remove() // Safe removal of element
                    score++
                    speed = 1 + Math.abs(score / 8)
                    if (score > highestScore) {
                        highScore = score
                        if(highScoreTextTimeCount == 0){
                            highScoreTextStatus = true
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f

        canvas.drawText("Score: $score", 80f, 80f, myPaint!!)
        canvas.drawText("Speed: $speed", 380f, 80f, myPaint!!)
        canvas.drawText("Last High Score: $highestScore", 680f, 80f, myPaint!!)

        if (highScoreTextStatus) {
            highScoreTextTimeCount++
            if (highScoreTextTimeCount < 500) {
                myPaint!!.color = Color.YELLOW
                myPaint!!.textSize = 60f
                canvas.drawText("New High Score!", 300f, 200f, myPaint!!)
            } else {
                highScoreTextStatus = false
            }
        }

        invalidate()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if(x1 < viewWidth / 2){
                    if(myCarPosition > 0){
                        myCarPosition--
                    }
                } else {
                    if (myCarPosition < 3){
                        myCarPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP ->{

            }
        }
        return true
    }

}