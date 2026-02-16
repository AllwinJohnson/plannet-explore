package com.anticosmic.data.datasource

import com.anticosmic.domain.model.Planet

class LocalPlanetDataSource {

    private val planets = listOf(
        Planet(
            id = "mercury",
            name = "Mercury",
            description = "The smallest planet in our solar system and closest to the Sun—is only slightly larger than Earth's Moon.",
            imageUrl = "https://images.unsplash.com/photo-1614730341194-75c60740b7d9?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3",
            distanceFromSun = "58 million km",
            gravity = "3.7 m/s²",
            temperature = "167°C"
        ),
        Planet(
            id = "venus",
            name = "Venus",
            description = "Second planet from the Sun and our closest planetary neighbor. Similar in structure and size to Earth.",
            imageUrl = "https://images.unsplash.com/photo-1614728853913-1e2201d9f68b?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3",
            distanceFromSun = "108 million km",
            gravity = "8.87 m/s²",
            temperature = "464°C"
        ),
        Planet(
            id = "earth",
            name = "Earth",
            description = "Our home planet is the third planet from the Sun, and the only place we know of so far that’s inhabited by living things.",
            imageUrl = "https://images.unsplash.com/photo-1614730341194-75c60740b7d9?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3", // Placeholder, need better earth
            distanceFromSun = "150 million km",
            gravity = "9.8 m/s²",
            temperature = "15°C"
        ),
        Planet(
            id = "mars",
            name = "Mars",
            description = "Dusty, cold, desert world with a very thin atmosphere. There is strong evidence that Mars was—billions of years ago—wetter and warmer, with a thick atmosphere.",
            imageUrl = "https://images.unsplash.com/photo-1614728423169-3f0c058e578a?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3",
            distanceFromSun = "228 million km",
            gravity = "3.71 m/s²",
            temperature = "-65°C"
        ),
        Planet(
            id = "jupiter",
            name = "Jupiter",
            description = "The biggest planet in our solar system. It's similar to a star, but it never got big enough to start burning.",
            imageUrl = "https://images.unsplash.com/photo-1614732414444-096e6f5252e5?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3",
            distanceFromSun = "778 million km",
            gravity = "24.79 m/s²",
            temperature = "-110°C"
        ),
        Planet(
            id = "saturn",
            name = "Saturn",
            description = "Adorned with a dazzling, complex system of icy rings, Saturn is unique in our solar system. The other giant planets have rings, but none are as spectacular as Saturn's.",
            imageUrl = "https://images.unsplash.com/photo-1614732484003-ef9881555dc3?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3",
            distanceFromSun = "1.4 billion km",
            gravity = "10.44 m/s²",
            temperature = "-140°C"
        ),
        Planet(
            id = "uranus",
            name = "Uranus",
            description = "Uranus is the seventh planet from the Sun, and has the third-largest diameter in our solar system. It was the first planet found with the aid of a telescope.",
            imageUrl = "https://images.unsplash.com/photo-1614732414444-096e6f5252e5?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3", // Placeholder
            distanceFromSun = "2.9 billion km",
            gravity = "8.69 m/s²",
            temperature = "-195°C"
        ),
        Planet(
            id = "neptune",
            name = "Neptune",
            description = "The first planet located through mathematical calculations rather than telescopic observation.",
            imageUrl = "https://images.unsplash.com/photo-1614732414444-096e6f5252e5?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3", // Placeholder
            distanceFromSun = "4.5 billion km",
            gravity = "11.15 m/s²",
            temperature = "-200°C"
        )
    )

    fun getAllPlanets(): List<Planet> = planets

    fun getPlanetById(id: String): Planet? = planets.find { it.id == id }
}
