package com.example.cats_mice_game.domain


data class GameStatsEntity(
    var id: Int,
    val totalClicks: Int,
    val correctClicks: Int,
    val gameTime: String,
    val percentClick: Int
)
