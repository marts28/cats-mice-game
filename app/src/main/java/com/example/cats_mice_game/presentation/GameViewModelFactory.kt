package com.example.cats_mice_game.presentation

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cats_mice_game.domain.GameSettings

class GameViewModelFactory(
    private val gameSettings: GameSettings,
    private val bitmap: Bitmap,
    private val application: Application
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameViewModel::class.java)){
            return GameViewModel(gameSettings, bitmap, application) as T
        }
        throw RuntimeException("Unkown viewmodel $modelClass")
    }
}