package com.example.cats_mice_game.presentation

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cats_mice_game.data.AppDatabase
import com.example.cats_mice_game.data.GameStatsDbModel
import com.example.cats_mice_game.domain.GameSettings
import com.example.cats_mice_game.domain.Mouse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.TimeSource

class GameViewModel(
    private var gameSettings: GameSettings,
    private val bitmap: Bitmap,
    private val application: Application
) : ViewModel() {

    private val gameStatsDao = AppDatabase.getInstance(application).getGameStatsDao()

    private val myCoroutineScope = CoroutineScope(Dispatchers.IO)

    private var miceList = mutableListOf<Mouse>()
    private var totalClicks = 0
    private var correctClicks = 0
    private val timeSource = TimeSource.Monotonic
    private var mark1: TimeSource.Monotonic.ValueTimeMark = timeSource.markNow()

    init {
        startGame()
    }

    private fun startGame() {
        for (i in 0 until gameSettings.numberOfMice) {
            val mouse = Mouse(bitmap, gameSettings.miceVelocity / 10f)
            miceList.add(mouse)
        }
    }

    fun update() {
        for (mouse in miceList) {
            mouse.update()
        }
    }

    fun draw(canvas: Canvas) {
        for (mouse in miceList) {
            mouse.draw(canvas)
        }
    }

    fun insert() {
        val gameTime = (timeSource.markNow() - mark1).inWholeMilliseconds
        val gameStatsDbModel = GameStatsDbModel(
            totalClicks = totalClicks,
            correctClicks = correctClicks,
            gameTime = gameTime
        )

        myCoroutineScope.launch {
            try {
                gameStatsDao.insertGameStats(gameStatsDbModel)
                Log.d("Test", "insert")
                if (gameStatsDao.getAllGameStats().size > 10) {
                    val first = gameStatsDao.getAllGameStats().first()
                    gameStatsDao.deleteGameStats(first.id)
                }
            } catch (e: Exception) {
                Log.d("Test", "$e")
            }
        }
    }

    fun checkMouseTouch(touchedPoint: Point) {
        for (mouse in miceList) {
            if (mouse.isMouseTouched(touchedPoint)) {
                val vibrator = application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
                correctClicks++
                break
            }
        }
        totalClicks++
    }


}