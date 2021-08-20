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

    /**
     *
     * At the initialization, we will retrieve asteroid data
     * and convert it into a list of Asteroid object,
     * then store it into the database
     *
     * */
    init {
        viewModelScope.launch {
            val asteroidJsonObject = JSONObject(asteroidRepository.getAsteroidData())
            val asteroidList = parseAsteroidsJsonResult(asteroidJsonObject)
            asteroidRepository.storeAsteroids(asteroidList)
        }
    }

    val asteroids  = asteroidRepository.asteroids

    /**
     * Will make the network request in a
     * background thread.
     * */

}