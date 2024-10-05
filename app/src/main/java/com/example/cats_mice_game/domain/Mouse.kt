package com.example.cats_mice_game.domain

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import com.example.cats_mice_game.R
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.math.abs
import kotlin.random.Random

class Mouse(private var bitmap:Bitmap, private val mouseVelocity: Float = 15f) {

    private var movingVectorX: Int = 0
    private var movingVectorY: Int = 0
    private var lastDrawNanoTime: Long = -1

    var currentPoint = Point(0, 0)
    private var destinationPoint = Point(0, 0)

    private var rotationAngle = 0f

    private var w = bitmap.width
    private var h = bitmap.height

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        currentPoint.x = screenWidth / 3
        currentPoint.y = screenHeight / 2


        h = bitmap.height
        w = bitmap.width
        newPoint()
    }


    fun newPoint() {
        destinationPoint.x = Random.nextInt(0, screenWidth - w)
        destinationPoint.y = Random.nextInt(0, screenHeight - h)


        movingVectorX = destinationPoint.x - currentPoint.x
        movingVectorY = destinationPoint.y - currentPoint.y
    }


    fun draw(canvas: Canvas) {


        val matrix = Matrix()


        matrix.postTranslate((-w / 2).toFloat(), (-h / 2).toFloat())
        matrix.postRotate(rotationAngle)
        matrix.postTranslate((currentPoint.x + w / 2).toFloat(), (currentPoint.y + h / 2).toFloat())
//        matrix.setScale(0.1f, 0.1f)

        canvas.drawBitmap(bitmap, matrix, null)

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

    private fun calculateRotationAngle(deltaX: Float, deltaY: Float): Float {
        return Math.toDegrees(Math.atan2(deltaY.toDouble(), deltaX.toDouble())).toFloat()
    }


    fun update() {

        val pointChanges = calculatePointChanges()
        if (abs(currentPoint.x - destinationPoint.x) > abs(pointChanges.x) && abs(currentPoint.y - destinationPoint.y) > abs(pointChanges.y)){
            currentPoint.x += pointChanges.x
            currentPoint.y += pointChanges.y
            rotationAngle = calculateRotationAngle(pointChanges.x.toFloat(), pointChanges.y.toFloat())
        }else{
            newPoint()
        }

    }



}