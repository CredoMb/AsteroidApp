package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class DetailViewModel(isHazardous: Boolean) : ViewModel() {

    private val _hazardousStatus = MutableLiveData<Boolean>()

    // The external LiveData for the hazardousStatus
    val hazardousStatus: LiveData<Boolean>
        get() = _hazardousStatus

    // Initialize the _hazardousStatus MutableLiveData.
    init {
        _hazardousStatus.value = isHazardous
    }

    /**
     * Returns the correct description based on
     * wether the asteroid is hazardous or not.
     * The returned value will be used as the
     * the content description of the detail icon
     *
     * */
    fun iconDescription()=
        when (hazardousStatus.value!!) {
            true ->"Icon for potentially hazardous asteroid"
            false ->"Icon for non hazardous asteroid"
        }
}