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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cats_mice_game.R
import com.example.cats_mice_game.databinding.FragmentGameBinding
import com.example.cats_mice_game.databinding.FragmentStartBinding
import com.example.cats_mice_game.presentation.GameViewModel
import com.example.cats_mice_game.presentation.GameViewModelFactory

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory by lazy {
        GameViewModelFactory(args.gameSettings, getMouseImage(), (context as Activity).application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }
    private lateinit var gameSurface: GameSurface

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        gameSurface = GameSurface(requireActivity(), viewModel)
        return gameSurface
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackPressCallbackWithSavingGameData()
    }

    private fun setOnBackPressCallbackWithSavingGameData(){

        val onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.insert()
                findNavController().navigate(R.id.action_gameFragment_to_startFragment)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

    }

    fun getMouseImage() = Bitmap.createScaledBitmap(
        BitmapFactory.decodeResource(resources, R.drawable.mouse),
        300,
        150,
        false
    )

    override fun onDestroyView() {
        super.onDestroyView()
    }
}