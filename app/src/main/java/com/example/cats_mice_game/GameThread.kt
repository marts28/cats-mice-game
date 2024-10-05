package com.example.cats_mice_game

import android.graphics.Canvas
import android.view.SurfaceHolder
import com.example.cats_mice_game.ui.GameSurface

class GameThread(
    private val surfaceHolder: SurfaceHolder,
    private val gameSurface: GameSurface
) : Thread() {

    private var averageFPS: Double = 0.toDouble()
    private var isRunning = false
    fun setRunning(isRunning: Boolean) {
        this.isRunning = isRunning
    }

    override fun run() {
        var startTime: Long
        var timeMillis = (1000 / MAX_FPS).toLong()
        var waitTime: Long
        var totalTime: Long = 0
        var frameCount = 0
        val targetTime = (1000 / MAX_FPS).toLong()

        while (isRunning) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas =  surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gameSurface.update()
                    gameSurface.draw(canvas!!)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis

            try {
                if (waitTime > 0)
                    sleep(waitTime)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            totalTime += System.nanoTime() - startTime
            frameCount++
            if (frameCount == MAX_FPS) {
                averageFPS = (1000 / (totalTime / frameCount / 1000000)).toDouble()
                frameCount = 0
                totalTime = 0
                println(averageFPS)
            }
        }
    }

    companion object {
        const val MAX_FPS = 60
        var canvas: Canvas? = null
    }

}