package com.example.cats_mice_game.data

import com.example.cats_mice_game.domain.GameStatsEntity
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class GameStatsMapper {

    fun mapDbModelToEntity(dbModel: GameStatsDbModel) =
        GameStatsEntity(
            dbModel.id,
            dbModel.totalClicks,
            dbModel.correctClicks,
            convertTime(dbModel.gameTime),
            calculatePercentOfCorrectClicks(dbModel.totalClicks, dbModel.correctClicks)
        )

    private fun convertTime(millis: Long): String {
        val stamp = Timestamp(millis)
        val date = Date(stamp.time)
        val pattern = "mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun calculatePercentOfCorrectClicks(totalClicks: Int, correctClicks: Int) =
        if (totalClicks == 0)
            0
        else
            (correctClicks.toFloat() / totalClicks.toFloat() * 100f).toInt()

}