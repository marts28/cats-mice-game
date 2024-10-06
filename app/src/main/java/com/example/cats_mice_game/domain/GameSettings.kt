package com.example.cats_mice_game.domain


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings (
    val numberOfMice: Int,
    val miceVelocity: Int
):Parcelable