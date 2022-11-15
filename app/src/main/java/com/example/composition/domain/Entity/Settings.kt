package com.example.composition.domain.Entity

data class Settings (
     val maxSumValue: Int,
     val minCountOfRightAnswers: Int,
     val minPercentOfRightAnswers: Int,
     val gameTimeInSeconds: Int
)