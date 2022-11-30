package com.example.composition.domain.entity

data class Questions (
    val sum: Int,
    val visibleNumber: Int,
    val option: List<Int>
        ) {
    val rightAnswer = sum - visibleNumber
}