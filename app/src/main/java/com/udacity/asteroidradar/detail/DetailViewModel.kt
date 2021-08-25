package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.util.Constants

class DetailViewModel(asteroid: Asteroid) : ViewModel() {

    private val _currentAsteroid = MutableLiveData<Asteroid>()

    // The external LiveData for the hazardousStatus
    val currentAsteroid: LiveData<Asteroid>
        get() = _currentAsteroid

    // Initialize the _hazardousStatus MutableLiveData.
    init {
        _currentAsteroid.value = asteroid
    }

    /**
     * Returns the correct description based on
     * wether the asteroid is hazardous or not.
     * The returned value will be used as the
     * the content description of the detail icon
     *
     * */
    fun iconDescription()=
        when (currentAsteroid.value!!.isPotentiallyHazardous!!) {
            true -> Constants.HAZARDOUS_DESCRIPTION
            false -> Constants.NON_HAZARDOUS_DESCRIPTION
        }
}