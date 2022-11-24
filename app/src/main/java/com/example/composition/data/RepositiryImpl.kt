package com.example.composition.data

import com.example.composition.domain.Entity.Level
import com.example.composition.domain.Entity.Questions
import com.example.composition.domain.Entity.Settings
import com.example.composition.domain.Repository.GameRepository
import com.example.composition.domain.UseCases.GenerateQuestionUseCase
import com.example.composition.domain.UseCases.GetSettingsUseCase
import kotlin.random.Random

object RepositiryImpl: GameRepository {
    override fun getGameSettings(level: Level): Settings {
        return when(level) {
            Level.TEST -> Settings(
                10,
                4,
            50,
                5
            )
            Level.EASY -> Settings(
                20,
                8,
            70,
                30
            )
            Level.NORMAL -> Settings(
                30,
                10,
            80,
                40
            )
            Level.HARD -> Settings(
                100,
                20,
            90,
                60
            )
        }
    }

    private const val MIN_ANSWER_VALUE = 1
    private const val MIN_SUM_VALUE = 2


    override fun generateQuestion(maxSumValue: Int, countOfAnswers: Int): Questions {
        val sumValue = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleValue = Random.nextInt(MIN_ANSWER_VALUE, sumValue)
        val options = HashSet<Int>()
        val rightAnswer = sumValue - visibleValue
        options.add(rightAnswer)
        val from = MIN_ANSWER_VALUE
        val to = maxSumValue - MIN_SUM_VALUE
        while (options.size < countOfAnswers) {
            options.add(Random.nextInt(from, to))
        }
        return Questions(sumValue, visibleValue, options.toList())
    }
}