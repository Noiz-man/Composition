package com.example.composition.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameOverBinding
import com.example.composition.domain.entity.Results

class GameOverFragment : Fragment() {

    private val args by navArgs<GameOverFragmentArgs>()

    private var _binding: FragmentGameOverBinding? = null
    private val binding: FragmentGameOverBinding
        get() = _binding ?: throw RuntimeException("FragmentGameOverBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRestart.setOnClickListener {
            retryGame()
        }
        setOnViews()

    }

    private fun setOnViews() {
        binding.dataResults = args.results
        with(binding) {
//            tvNeedAnswers.text = String.format(getString(R.string.NeedCountOfRightAnswers),
//                args.results.settings.minCountOfRightAnswers)
            ivGameOver.setImageResource(if (args.results.winner) {
                R.drawable.goodsmile
            } else R.drawable.badsmile)
//            tvRightAswers.text = String.format(getString(R.string.CountOfRightAnswers),
//                args.results.countOfRightAnswers)
//            tvNeedPercent.text = String.format(getString(R.string.NeedPercentsOfRightAnswers),
//                args.results.settings.minPercentOfRightAnswers)
            tvRightPercent.text = String.format(getString(R.string.PercentOfRightAnswers),
                getPecentage())
        }

    }

    private fun getPecentage():Int {
        return ((args.results.countOfRightAnswers / args.results.countOfQuestions.toDouble()) * 100).toInt()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}