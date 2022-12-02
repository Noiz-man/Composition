package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Results

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(args.level, requireActivity().application))[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dataViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        endGame()
    }

    private fun launchGameOver(results: Results) {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment(
            results))
    }

    private fun endGame() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameOver(it)
        }
    }
}
