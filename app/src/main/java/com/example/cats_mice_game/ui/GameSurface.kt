package com.example.cats_mice_game.ui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.cats_mice_game.GameThread
import com.example.cats_mice_game.R
import com.example.cats_mice_game.domain.GameSettings
import com.example.cats_mice_game.presentation.GameViewModel
import com.example.cats_mice_game.presentation.GameViewModelFactory

class GameSurface(context: Context, private val viewModel: GameViewModel) : SurfaceView(context),
    SurfaceHolder.Callback {

    private var thread: GameThread? = null

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        isFocusable = true
        Log.d("Test", "Surface init")
    }



    override fun surfaceCreated(p0: SurfaceHolder) {
        if (thread == null){
            holder.addCallback(this)
            thread = GameThread(holder, this)
            isFocusable = true
        }
        Log.d("Test", "Surface created")
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
        Log.d("Test","surface destroyed" )
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

//        return super.onTouchEvent(event)
    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas!!.drawColor(Color.WHITE)
        viewModel.draw(canvas)

    }

    fun update() {

        viewModel.update()
    }


}