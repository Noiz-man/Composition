package com.example.composition.domain.Entity

data class Results (
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val settings: Settings
        ) : java.io.Serializable