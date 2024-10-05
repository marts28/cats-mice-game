package com.example.cats_mice_game.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.cats_mice_game.GameThread
import com.example.cats_mice_game.R
import com.example.cats_mice_game.domain.Mouse

class GameSurface(context: Context): SurfaceView(context), SurfaceHolder.Callback {

    private val thread: GameThread
    private val mouse:Mouse

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        isFocusable = true
        var bit = BitmapFactory.decodeResource(resources, R.drawable.mouse)
        bit = Bitmap.createScaledBitmap(bit, 300, 150, false)

        mouse = Mouse(bit)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while(retry){
            try {
                thread.setRunning(false)
                thread.join()
            }catch (e:Exception){
                Log.d("Exception", e.toString())
            }
            retry = false
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event!!.action){
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {}
        }
        return true

//        return super.onTouchEvent(event)
    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas!!.drawColor(Color.WHITE)
        mouse.draw(canvas)
    }

    fun update(){
        mouse.update()
    }


}