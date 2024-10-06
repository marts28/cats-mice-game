package com.example.cats_mice_game.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameStatsDao {

    @Insert
    suspend fun insertGameStats(gameStatsDbModel: GameStatsDbModel)

    @Query("select * from game_stats")
    fun getAllGameStats():List<GameStatsDbModel>
    @Query("delete from game_stats where id=:id")
    suspend fun deleteGameStats(id: Int)

}