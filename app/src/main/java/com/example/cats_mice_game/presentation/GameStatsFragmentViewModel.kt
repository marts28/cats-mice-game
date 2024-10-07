package com.example.cats_mice_game.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cats_mice_game.data.AppDatabase
import com.example.cats_mice_game.data.GameStatsMapper
import com.example.cats_mice_game.domain.GameStatsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GameStatsFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val gameStatsDao = AppDatabase.getInstance(application).getGameStatsDao()
    private val mapper = GameStatsMapper()

    val gameStatsList = MutableLiveData<List<GameStatsEntity>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            gameStatsList.postValue(gameStatsDao.getAllGameStats().map { dbModel ->
                mapper.mapDbModelToEntity(dbModel)
            }.reversed())
        }
    }

}