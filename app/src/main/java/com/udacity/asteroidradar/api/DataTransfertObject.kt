package com.udacity.asteroidradar

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: List<NetworkAsteroid>)

/**
 * Represent a Asteroid from the API.
 *
 * I don't think I need this
 */
@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val id: String,
    val absolute_magnitude_h: String,
    val estimated_diameter_max: Int,
    val is_potentially_hazardous_asteroid: Double,
    val kilometers_per_second: String,
    val astronomical: String)

// Do we build all the small objects ?
// I think so