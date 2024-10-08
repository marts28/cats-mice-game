package com.example.cats_mice_game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cats_mice_game.R
import com.example.cats_mice_game.databinding.FragmentStartBinding
import com.example.cats_mice_game.domain.GameSettings

class StartFragment: Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding
        get() = _binding ?: throw RuntimeException("Start Binding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonsClickListeners()
        setSeekBarsChangeListeners()
    }

    private fun setSeekBarsChangeListeners() {
        binding.sbNumberOfMice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.tvNumberOfMice.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.sbMiceVelocity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.tvMiceVelocity.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    private fun setButtonsClickListeners() {
        with(binding) {
            buttonStartGame.setOnClickListener {
                launchGame()
            }
            buttonLookGameStats.setOnClickListener {
                launchStatsScreen()
            }
        }
    }

    private fun launchStatsScreen() {
        findNavController().navigate(R.id.action_startFragment_to_statsFragment)
    }

    private fun launchGame(){
        findNavController().navigate(StartFragmentDirections.actionStartFragmentToGameFragment(
            GameSettings(
                binding.tvNumberOfMice.text.toString().toInt(),
                binding.tvMiceVelocity.text.toString().toInt()
            )
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}