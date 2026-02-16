package com.anticosmic.domain.model

/**
 * Domain model representing a Planet.
 */
data class Planet(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val distanceFromSun: String, // Added for extra detail
    val gravity: String = "Unknown",
    val temperature: String = "Unknown"
)
