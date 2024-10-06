package com.example.cats_mice_game.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cats_mice_game.R
import com.example.cats_mice_game.domain.GameSettings
import com.example.cats_mice_game.domain.Mouse

class GameViewModel(
    private var gameSettings: GameSettings,
    private val bitmap: Bitmap
):ViewModel() {


    private var miceList = mutableListOf<Mouse>()

    init {
        startGame()
    }
    private fun startGame(){
        for (i in 0 until gameSettings.numberOfMice){
            val mouse = Mouse(bitmap, gameSettings.miceVelocity/10f)
            miceList.add(mouse)
        }
    }

    fun stopGame(){
        miceList.clear()
    }

    fun update(){
        for(mouse in miceList){
            mouse.update()
        }
    }

    fun draw(canvas: Canvas) {
        for(mouse in miceList){
            mouse.draw(canvas)
        }
    }
    fun insert(){

    }


}