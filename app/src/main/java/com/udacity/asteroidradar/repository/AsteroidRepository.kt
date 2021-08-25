package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getCurrentDateFormatted
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDatabaseModel
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val database: AsteroidsDatabase) {

    /**
     * Gets a list of asteroids from the database
     * to be shown on the main fragment.
     */
    val asteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDao.getAsteroids(getCurrentDateFormatted())) {
                it.asDomainModel()
            }

    /**
     * Fetches asteroid data from the nasa api
     * and returns a Json String.
     * */
    suspend fun getAsteroidData() = withContext(Dispatchers.IO) {
        val currFormattedDate = getCurrentDateFormatted()
        Network.nearObjects.getNearObjects(currFormattedDate, Constants.API_KEY).execute().body()!!
    }

    /**
     * Fetches image of the day from the nasa api
     * and returns a Json String.
     * */
    suspend fun getImgOfTheDay() = withContext(Dispatchers.IO) {
        Network.imgOfTheDay.getImgOfTheDay(Constants.API_KEY).await()
    }

    /**
     * Store a list of asteroids in the offline cache.
     * Will be used right after fetching data from the api.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the asteroids for use, observe [asteroids]
     *
     */
    suspend fun storeAsteroids(asteroids: ArrayList<Asteroid>) {
        withContext(Dispatchers.IO) {
            // The data base insert was supposed to get
            // vararg, therefore the data base Model was
            // suppose to return
            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())

        }
    }

}