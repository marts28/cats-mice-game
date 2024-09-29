package com.example.cats_mice_game.domain

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect

class Mouse(private var rectangle: Rect, private var color:Int) {

    fun draw(canvas: Canvas) {
        val paint = Paint().apply {
            color = this@Mouse.color
        }
        canvas.drawRect(rectangle,paint)
    }

    fun update(point: Point) {
        rectangle.set(point.x - rectangle.width()/2,
            point.y - rectangle.width()/2,
            point.x + rectangle.width()/2,
            point.y + rectangle.width()/2
        )
    }

}