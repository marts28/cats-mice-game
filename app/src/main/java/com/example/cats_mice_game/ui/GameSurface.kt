package com.example.cats_mice_game.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.cats_mice_game.GameThread
import com.example.cats_mice_game.presentation.GameViewModel

class GameSurface(context: Context, private val viewModel: GameViewModel) : SurfaceView(context),
    SurfaceHolder.Callback {

    private var thread: GameThread? = null

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        isFocusable = true
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        if (thread == null){
            holder.addCallback(this)
            thread = GameThread(holder, this)
            isFocusable = true
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        thread?.setRunning(true)
        thread?.start()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread?.setRunning(false)
                thread?.join()
            } catch (e: Exception) {
                Log.d("Test", e.toString())
            }
            retry = false
        }
        thread = null
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                viewModel.checkMouseTouch(
                    Point(
                        event.x.toInt(),
                        event.y.toInt()
                    )
                )
            }
        }
        return true
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE)
        viewModel.draw(canvas)

    }

    fun update() {
        viewModel.update()
    }

}