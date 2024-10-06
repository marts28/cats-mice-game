package com.example.cats_mice_game.ui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.cats_mice_game.R
import com.example.cats_mice_game.databinding.FragmentGameBinding
import com.example.cats_mice_game.databinding.FragmentStartBinding
import com.example.cats_mice_game.presentation.GameViewModel
import com.example.cats_mice_game.presentation.GameViewModelFactory

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("Game Binding is null")

    private val args by navArgs<GameFragmentArgs>()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return GameSurface(requireActivity(), args.gameSettings)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}