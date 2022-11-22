package com.example.composition.domain.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Results (
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val settings: Settings
        ) : Parcelable