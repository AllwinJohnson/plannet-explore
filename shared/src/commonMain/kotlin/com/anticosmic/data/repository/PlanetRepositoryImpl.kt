package com.anticosmic.data.repository

import com.anticosmic.data.datasource.LocalPlanetDataSource
import com.anticosmic.domain.model.Planet
import com.anticosmic.domain.repository.PlanetRepository

class PlanetRepositoryImpl(
    private val localDataSource: LocalPlanetDataSource
) : PlanetRepository {
    
    override suspend fun getPlanets(): List<Planet> {
        return localDataSource.getAllPlanets()
    }

    override suspend fun getPlanet(id: String): Planet? {
        return localDataSource.getPlanetById(id)
    }
}
