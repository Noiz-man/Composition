package com.example.composition.domain.Entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level: Parcelable {
    TEST, EASY, NORMAL, HARD
}