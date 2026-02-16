package com.anticosmic.domain.repository

import com.anticosmic.domain.model.Planet

/**
 * Repository interface for fetching Planet data.
 */
interface PlanetRepository {
    suspend fun getPlanets(): List<Planet>
    suspend fun getPlanet(id: String): Planet?
}
