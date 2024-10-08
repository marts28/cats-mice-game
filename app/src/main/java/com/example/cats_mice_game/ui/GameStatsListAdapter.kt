package com.example.cats_mice_game.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.cats_mice_game.databinding.ItemGameStatBinding
import com.example.cats_mice_game.domain.GameStatsEntity
import com.example.cats_mice_game.presentation.GameStatsItemCallback

class GameStatsListAdapter: ListAdapter<GameStatsEntity, GameStatsViewHolder>(GameStatsItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameStatsViewHolder {
        val binding = ItemGameStatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameStatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameStatsViewHolder, position: Int) {
        val gameStats = getItem(position)
        with(holder.binding){
            tvCorrectClicks.text = gameStats.correctClicks.toString()
            tvGameTime.text =  gameStats.gameTime
            tvTotalClicks.text = gameStats.totalClicks.toString()
            tvPercentClicks.text = gameStats.percentClick.toString()
        }
    }
}