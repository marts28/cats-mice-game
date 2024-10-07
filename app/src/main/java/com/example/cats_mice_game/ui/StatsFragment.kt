package com.example.cats_mice_game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cats_mice_game.databinding.FragmentStartBinding
import com.example.cats_mice_game.databinding.FragmentStatsBinding
import com.example.cats_mice_game.presentation.GameStatsFragmentViewModel

class StatsFragment:Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding: FragmentStatsBinding
        get() = _binding ?: throw RuntimeException("Start Binding is null")

    private val viewModel by lazy{
        ViewModelProvider(this)[GameStatsFragmentViewModel::class.java]
    }

    private val myAdapter = GameStatsListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvGameStats.adapter = myAdapter
        viewModel.gameStatsList.observe(viewLifecycleOwner){
            myAdapter.submitList(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}