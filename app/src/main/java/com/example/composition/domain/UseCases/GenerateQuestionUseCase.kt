package com.example.composition.domain.UseCases

import com.example.composition.domain.entity.Questions
import com.example.composition.domain.Repository.GameRepository

class GenerateQuestionUseCase(
    val repository: GameRepository
) {
    operator fun invoke (maxSumValue: Int): Questions{
        return repository.generateQuestion(maxSumValue, COUNT_OF_ANSWERS)

    }

    companion object{
        private const val COUNT_OF_ANSWERS = 6
    }
}