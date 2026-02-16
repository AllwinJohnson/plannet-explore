package com.anticosmic.domain.usecase

import com.anticosmic.domain.model.Planet
import com.anticosmic.domain.repository.PlanetRepository

class GetPlanetDetailsUseCase(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke(planetId: String): Planet? {
        return repository.getPlanet(planetId)
    }
}
