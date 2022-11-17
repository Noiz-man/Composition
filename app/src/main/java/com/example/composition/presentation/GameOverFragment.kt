package com.example.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.FragmentManager
import com.example.composition.R
import com.example.composition.databinding.FragmentGameOverBinding
import com.example.composition.domain.Entity.Results
import com.example.composition.domain.Entity.Settings

class GameOverFragment : Fragment() {
    private lateinit var results: Results

    private var _binding: FragmentGameOverBinding? = null
    private val binding: FragmentGameOverBinding
        get() = _binding ?: throw RuntimeException("FragmentGameOverBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }

            })
        binding.btnRestart.setOnClickListener {
            retryGame()
        }
    }

    private fun parseArgs() {
        results = arguments?.getSerializable(KEY_RESULTS) as Results
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME_GAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        private const val KEY_RESULTS = "results"

        fun newInstance(results: Results): GameOverFragment {
            return GameOverFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_RESULTS, results)
                }
            }
        }
    }
}