package com.example.cats_mice_game.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.cats_mice_game.domain.GameStatsEntity

class GameStatsItemCallback: DiffUtil.ItemCallback<GameStatsEntity>() {
    override fun areItemsTheSame(oldItem: GameStatsEntity, newItem: GameStatsEntity) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GameStatsEntity, newItem: GameStatsEntity) =
        oldItem == newItem
}