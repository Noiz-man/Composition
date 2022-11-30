package com.example.composition.domain.UseCases

import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Settings
import com.example.composition.domain.Repository.GameRepository

class GetSettingsUseCase(
    val repository: GameRepository
) {
    operator fun invoke(level: Level): Settings {
        return repository.getGameSettings(level)
    }
}