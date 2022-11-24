package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.Entity.Level
import com.example.composition.domain.Entity.Results
import com.example.composition.domain.Entity.Settings

class GameFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(level, requireActivity().application))[GameViewModel::class.java]
    }

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
        timer()
        setQuestion()
        answers()
        textProgress()
        progressBar()
        endGame()

        with(binding) {
            tvAnswer1.setOnClickListener {
                viewModel.chooseAnswer(tvAnswer1.text.toString().toInt())
            }
            tvAnswer2.setOnClickListener {
                viewModel.chooseAnswer(tvAnswer2.text.toString().toInt())
            }
            tvAnswer3.setOnClickListener {
                viewModel.chooseAnswer(tvAnswer3.text.toString().toInt())
            }
            tvAnswer4.setOnClickListener {
                viewModel.chooseAnswer(tvAnswer4.text.toString().toInt())
            }
            tvAnswer5.setOnClickListener {
                viewModel.chooseAnswer(tvAnswer5.text.toString().toInt())
            }
            tvAnswer6.setOnClickListener {
                viewModel.chooseAnswer(tvAnswer6.text.toString().toInt())
            }
        }
    }

    private fun launchGameOver(results: Results) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameOverFragment.newInstance(results))
            .addToBackStack(null)
            .commit()
    }

    private fun endGame() {
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameOver(it)
        }
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

    private fun timer() {
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTime.text = it
        }
    }

    private fun setQuestion() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvSummand.text = it.visibleNumber.toString()
        }
    }

    private fun textProgress() {
        viewModel.progresAnswers.observe(viewLifecycleOwner) {
            binding.tvCurrentResult.text = it
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColor(it)
            binding.tvCurrentResult.setTextColor(color)
        }
    }

    private fun progressBar() {
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.progress = it
        }

        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColor(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
    }

    private fun answers() {
        viewModel.question.observe(viewLifecycleOwner) {
            binding.tvAnswer1.text = it.option[0].toString()
            binding.tvAnswer2.text = it.option[1].toString()
            binding.tvAnswer3.text = it.option[2].toString()
            binding.tvAnswer4.text = it.option[3].toString()
            binding.tvAnswer5.text = it.option[4].toString()
            binding.tvAnswer6.text = it.option[5].toString()
        }
    }

    private fun getColor(state: Boolean): Int {
        val color = if (state) {
            android.R.color.holo_green_light
        } else android.R.color.holo_red_light
        return ContextCompat.getColor(requireContext(), color)
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
