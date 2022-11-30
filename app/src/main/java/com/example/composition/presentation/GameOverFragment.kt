package com.example.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import com.example.composition.R
import com.example.composition.databinding.FragmentGameOverBinding
import com.example.composition.domain.entity.Results

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

        setOnViews()

    }

    private fun setOnViews() {
        with(binding) {
            tvNeedAnswers.text = String.format(getString(R.string.NeedCountOfRightAnswers),
                results.settings.minCountOfRightAnswers)
            ivGameOver.setImageResource(if (results.winner) {
                R.drawable.goodsmile
            } else R.drawable.badsmile)
            tvRightAswers.text = String.format(getString(R.string.CountOfRightAnswers),
                results.countOfRightAnswers)
            tvNeedPercent.text = String.format(getString(R.string.NeedPercentsOfRightAnswers),
                results.settings.minPercentOfRightAnswers)
            tvRightPercent.text = String.format(getString(R.string.PercentOfRightAnswers),
                getPecentage())
        }

    }

    private fun getPecentage():Int {
        return ((results.countOfRightAnswers / results.countOfQuestions.toDouble()) * 100).toInt()

    }

    private fun parseArgs() {
        requireArguments().getParcelable<Results>(KEY_RESULTS)?.let {
            results = it
        }
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
                    putParcelable(KEY_RESULTS, results)
                }
            }
        }
    }
}