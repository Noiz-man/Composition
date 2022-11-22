package com.example.composition.presentation

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.Entity.Level
import com.example.composition.domain.Entity.Results
import com.example.composition.domain.Entity.Settings

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvAnswer1.setOnClickListener {
            launchGameOver(Results(
                true,
                12,
                14,
                Settings(24,
                12,
                45,
                23)))
        }
        binding.tvAnswer2.setOnClickListener { }
        binding.tvAnswer3.setOnClickListener { }
        binding.tvAnswer4.setOnClickListener { }
        binding.tvAnswer5.setOnClickListener { }
        binding.tvAnswer6.setOnClickListener { }
    }

    private fun launchGameOver(results: Results) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameOverFragment.newInstance(results))
            .addToBackStack(null)
            .commit()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val KEY_LEVEL = "level"
        const val NAME_GAME = "GameFragment"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}
