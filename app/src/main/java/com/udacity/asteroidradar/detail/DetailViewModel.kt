package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class DetailViewModel(asteroid: Asteroid, app: Application) : AndroidViewModel(app) {

    private val _selectedAsteroid = MutableLiveData<Asteroid>()

    // The external LiveData for the SelectedAsteroid
    val selectedAsteroid: LiveData<Asteroid>
        get() = _selectedAsteroid

    // Initialize the _selectedAsteroid MutableLiveData
    init {
        _selectedAsteroid.value = asteroid
    }

    val displayAsteroidImg = Transformations.map(_selectedAsteroid) {
        /*app.applicationContext.getDrawable(
                when(it.isPotentiallyHazardous){
                    true -> R.drawable.asteroid_hazardous
                    false -> R.drawable.asteroid_safe
                }
        )*/
        ContextCompat.getDrawable(app,
                when (it.isPotentiallyHazardous) {
                    true -> R.drawable.asteroid_safe
                    false -> R.drawable.asteroid_safe
                }
        )
    }
}