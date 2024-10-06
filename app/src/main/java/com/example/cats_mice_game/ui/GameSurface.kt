package com.example.cats_mice_game.ui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
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

class GameSurface(context: Context, gameSettings: GameSettings) : SurfaceView(context),
    SurfaceHolder.Callback {

    private val thread: GameThread

    private val viewModelFactory by lazy {
        GameViewModelFactory(gameSettings, getMouseImage(), (context as Activity).application)
    }

    private lateinit var viewModel:GameViewModel

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewModel = ViewModelProvider(
            findViewTreeViewModelStoreOwner()!!,
            viewModelFactory
        )[GameViewModel::class.java]
    }

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        isFocusable = true

    }

    fun getMouseImage() = Bitmap.createScaledBitmap(
        BitmapFactory.decodeResource(resources, R.drawable.mouse),
        300,
        150,
        false
    )

    override fun surfaceCreated(p0: SurfaceHolder) {
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
            }
            retry = false
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {

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