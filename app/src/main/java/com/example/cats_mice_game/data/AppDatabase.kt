package com.example.cats_mice_game.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [GameStatsDbModel::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getGameStatsDao(): GameStatsDao
    companion object{

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "games_stats.dp"

        fun getInstance(application: Application):AppDatabase{
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let{
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }

}