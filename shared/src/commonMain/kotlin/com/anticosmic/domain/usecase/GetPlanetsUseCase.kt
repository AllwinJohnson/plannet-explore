package com.anticosmic.domain.usecase

import com.anticosmic.domain.model.Planet
import com.anticosmic.domain.repository.PlanetRepository

class GetPlanetsUseCase(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(): List<Planet> {
        return repository.getPlanets()
    }
}
