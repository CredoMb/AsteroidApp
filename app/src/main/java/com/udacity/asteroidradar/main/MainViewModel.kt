package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.getDatabase
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    //
    // private

    // Internally, we use a MutableLiveData to handle navigation to the selected asteroid
    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

    // The external immutable LiveData for the navigation Asteroid
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    val _imgOfTheDay = MutableLiveData<PictureOfDay>()

    val imgOfTheDay: LiveData<PictureOfDay>
        get() = _imgOfTheDay

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
            // livedata variable. If the image of the day is a video,
            // turn the live data to null
            _imgOfTheDay.value = asteroidRepository.getImgOfTheDay()

            if (imgOfTheDay.value!!.mediaType.equals("video"))
                _imgOfTheDay.value = PictureOfDay("","","app\\src\\main\\res\\drawable\\placeholder_picture_of_day.xml")

            //
        }
    }

    /**
     *  Get asteroids from the database and
     *  store it in this variable
     *
     *  */
    val asteroids  = asteroidRepository.asteroids

    /**
     * When the asteroid is clicked, set the [_navigateToSelectedAsteroid] [MutableLiveData]
     * @param asteroid The [Asteroid] that was clicked on.
     */
    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedAsteroid is set to null
     */
    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

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