package com.example.cats_mice_game.domain

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import kotlin.math.abs
import kotlin.random.Random

class Mouse(private var rectangle: Rect, private var color: Int) {

    private var movingVectorX = 10
    private var movingVectorY = 5

    private var lastDrawNanoTime: Long = -1

    val mouseVelocity = 50f


    var currentPoint = Point(0, 0)

    var destinationPoint = Point(0, 0)

    var w = 15
    var h = 15

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        currentPoint.x = screenWidth / 3
        currentPoint.y = screenHeight / 2
        newPoint()
    }


    fun newPoint() {
        destinationPoint.x = Random.nextInt(0, screenWidth - w)
        destinationPoint.y = Random.nextInt(0, screenHeight - h)


        movingVectorX = destinationPoint.x - currentPoint.x
        movingVectorY = destinationPoint.y - currentPoint.y
    }


    fun draw(canvas: Canvas) {
        val paint = Paint().apply {
            color = this@Mouse.color
        }
        canvas.drawRect(rectangle, paint)
        lastDrawNanoTime = System.nanoTime()
    }

    fun calculatePointChanges():Point{
        val now = System.nanoTime()

        if (lastDrawNanoTime == -1L) {
            lastDrawNanoTime = now
        }

        val deltaTime = ((now - lastDrawNanoTime) / 1000000).toInt()

        val distance = mouseVelocity * deltaTime

        val movingVectorLength =
            Math.sqrt((movingVectorX * movingVectorX + movingVectorY * movingVectorY).toDouble())
        val changeX= (distance * movingVectorX / movingVectorLength).toInt()
        val changeY= (distance * movingVectorY / movingVectorLength).toInt()
        return Point(changeX, changeY)
    }


    fun update() {

        val pointChanges = calculatePointChanges()
        if (abs(currentPoint.x - destinationPoint.x) > abs(pointChanges.x) && abs(currentPoint.y - destinationPoint.y) > abs(pointChanges.y)){
            currentPoint.x += pointChanges.x
            currentPoint.y += pointChanges.y
            rectangle.set(
                currentPoint.x - rectangle.width() / 2,
                currentPoint.y - rectangle.width() / 2,
                currentPoint.x + rectangle.width() / 2,
                currentPoint.y + rectangle.width() / 2
            )
        }else{
            newPoint()
        }

    }

}