package com.udacity.asteroidradar.main

import android.app.Application
import android.app.ProgressDialog.show
import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getCurrentDateFormatted
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    //
    // private

    /**
     *
     * At the initialization, we will retrieve asteroid data
     * and convert it into a list of Asteroid object,
     * then store it into the database.
     *
     * */
    init {
        viewModelScope.launch {
            // Get the list of asteroids and store it into the
            // data base
            val asteroidJsonObject = JSONObject(asteroidRepository.getAsteroidData())
            val asteroidList = parseAsteroidsJsonResult(asteroidJsonObject)
            asteroidRepository.storeAsteroids(asteroidList)

            // Fetch the image of the day and store it in a
            // variable
            val imgOfTheDay = asteroidRepository.getImgOfTheDay()
            Log.i("sikama ",imgOfTheDay.hdurl)
        }
    }

    /**
     *  Get asteroids from the database and
     *  store it in this variable
     *
     *  */
    val asteroids  = asteroidRepository.asteroids

    /**
     * Factory for constructing MainViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}