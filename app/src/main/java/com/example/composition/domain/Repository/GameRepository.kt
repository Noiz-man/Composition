package com.example.composition.domain.Repository

import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Questions
import com.example.composition.domain.entity.Settings

interface GameRepository {

    fun getGameSettings(level: Level): Settings

    fun generateQuestion(
        maxSumValue: Int,
        countOfAnswwrs: Int
    ): Questions
}