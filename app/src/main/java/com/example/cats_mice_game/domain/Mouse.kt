package com.example.cats_mice_game.domain

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Point
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt
import kotlin.random.Random

class Mouse(private var bitmap: Bitmap, private val mouseVelocity: Float = 15f) {

    private var movingVectorX: Int = 0
    private var movingVectorY: Int = 0
    private var lastDrawNanoTime: Long = -1

    private var currentPoint = Point(0, 0)
    private var destinationPoint = Point(0, 0)

    private var rotationAngle = 0f

    private var w = bitmap.width
    private var h = bitmap.height

    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        currentPoint.x = Random.nextInt(0, screenWidth - w)
        currentPoint.y = Random.nextInt(0, screenHeight - h)

        createNewPoint()
        rotationAngle =
            calculateRotationAngle((currentPoint.x - destinationPoint.x).toFloat(), (currentPoint.y - destinationPoint.y).toFloat())
    }

    private fun createNewPoint() {
        destinationPoint.x = Random.nextInt(0, screenWidth - w)
        destinationPoint.y = Random.nextInt(0, screenHeight - h)

        movingVectorX = destinationPoint.x - currentPoint.x
        movingVectorY = destinationPoint.y - currentPoint.y
    }

    fun isMouseTouched(touchedPoint: Point) =
        touchedPoint.x >= currentPoint.x && touchedPoint.x <= (currentPoint.x + w)
                && touchedPoint.y >= currentPoint.y && touchedPoint.y <= (currentPoint.y + h)

    fun draw(canvas: Canvas) {

        val matrix = Matrix()
        matrix.postTranslate((-w / 2).toFloat(), (-h / 2).toFloat())
        matrix.postRotate(rotationAngle)
        matrix.postTranslate((currentPoint.x + w / 2).toFloat(), (currentPoint.y + h / 2).toFloat())

        canvas.drawBitmap(bitmap, matrix, null)

        lastDrawNanoTime = System.nanoTime()
    }

    private fun calculatePointChanges(): Point {
        val now = System.nanoTime()

        if (lastDrawNanoTime == -1L) {
            lastDrawNanoTime = now
        }

        val deltaTime = ((now - lastDrawNanoTime) / 1000000).toInt()

        val distance = mouseVelocity * deltaTime

        val movingVectorLength =
            sqrt((movingVectorX * movingVectorX + movingVectorY * movingVectorY).toDouble())
        val changeX = (distance * movingVectorX / movingVectorLength).toInt()
        val changeY = (distance * movingVectorY / movingVectorLength).toInt()
        return Point(changeX, changeY)
    }

    private fun calculateRotationAngle(deltaX: Float, deltaY: Float): Float {
        return Math.toDegrees(atan2(deltaY.toDouble(), deltaX.toDouble())).toFloat()+180
    }

    fun update() {

        val pointChanges = calculatePointChanges()
        if (abs(currentPoint.x - destinationPoint.x) > abs(pointChanges.x) &&
            abs(currentPoint.y - destinationPoint.y) > abs(pointChanges.y)
        ) {
            currentPoint.x += pointChanges.x
            currentPoint.y += pointChanges.y
        } else {
            createNewPoint()
            rotationAngle =
                calculateRotationAngle((currentPoint.x - destinationPoint.x).toFloat(), (currentPoint.y - destinationPoint.y).toFloat())
        }

    }


}