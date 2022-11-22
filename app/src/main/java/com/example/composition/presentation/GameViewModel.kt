package com.example.composition.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composition.R
import com.example.composition.data.RepositiryImpl
import com.example.composition.domain.Entity.Level
import com.example.composition.domain.Entity.Questions
import com.example.composition.domain.Entity.Results
import com.example.composition.domain.Entity.Settings
import com.example.composition.domain.UseCases.GenerateQuestionUseCase
import com.example.composition.domain.UseCases.GetSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var settings: Settings
    private lateinit var level: Level
    private val context = application
    private val repository = RepositiryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getSettingsUseCase = GetSettingsUseCase(repository)
    private var timer: CountDownTimer? = null
    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private val _question = MutableLiveData<Questions>()
    val question: LiveData<Questions>
        get() = _question

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private val _progresAnswers = MutableLiveData<String>()
    val progresAnswers: LiveData<String>
        get() = _progresAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<Results>()
    val gameResult: LiveData<Results>
    get() = _gameResult


    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        getQuestion()
    }

    private fun percentOfRightAnswers() {
        val minCountAnswers = settings.minCountOfRightAnswers
        val percent = ((countOfRightAnswers / countOfQuestions.toDouble()) * PERCENTS).toInt()
        _percentOfRightAnswers.value = percent
        _progresAnswers.value = String.format(context.resources.getString(
            R.string.progresACountAnswers), countOfRightAnswers, minCountAnswers)
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= minCountAnswers
        _enoughPercentOfRightAnswers.value = percent >= settings.minPercentOfRightAnswers
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.settings = getSettingsUseCase(level)
        _minPercent.value = settings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            settings.gameTimeInSeconds * MILISECONDS_IN_SECOND,
            MILISECONDS_IN_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                gameFinished()
            }
        }
        timer?.start()
    }

    private fun gameFinished() {
        val result = Results(
            enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            settings
        )
        _gameResult.value = result
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val secondsTotal = millisUntilFinished / MILISECONDS_IN_SECOND
        val minuts = secondsTotal / SECONDS_IN_MINUTE
        val seconds = secondsTotal - (minuts * SECONDS_IN_MINUTE)
        return String.format("%02d:%02d", minuts, seconds)
    }

    private fun getQuestion() {
        _question.value = generateQuestionUseCase(settings.maxSumValue)
    }

    private fun chooseAnswer(answer: Int) {
        isAnswerRight(answer)
        percentOfRightAnswers()
        getQuestion()
    }

    private fun isAnswerRight(answer: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (answer == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        const val MILISECONDS_IN_SECOND = 1000L
        const val SECONDS_IN_MINUTE = 60
        const val PERCENTS = 100
    }
}