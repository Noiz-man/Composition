package com.example.composition.domain.Repository

import com.example.composition.domain.Entity.Level
import com.example.composition.domain.Entity.Questions
import com.example.composition.domain.Entity.Settings

interface GameRepository {

    fun getGameSettings(level: Level): Settings

    fun generateQuestion(
        maxSumValue: Int,
        countOfAnswwrs: Int
    ): Questions
}