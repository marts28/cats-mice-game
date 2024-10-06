package com.example.cats_mice_game.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "game_stats")
data class GameStatsDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val totalClicks: Int,
    val correctClicks: Int,
    val gameTime: Long
)
