package com.udacity.asteroidradar.main

import android.app.ProgressDialog.show
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.getCurrentDateFormatted
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private var _asteroidList = MutableLiveData<ArrayList<Asteroid>>()

    val asteroidList: LiveData<ArrayList<Asteroid>>
        get() = _asteroidList

    // //_asteroidList.value = parseAsteroidsJsonResult(JSONObject(nearObjectsString))
    init {
        viewModelScope.launch {
            _asteroidList.value = parseAsteroidsJsonResult(JSONObject(getAsteroidData()))
        }
    }


    suspend fun getAsteroidData() = withContext(Dispatchers.IO) {
        val currFormattedDate =  getCurrentDateFormatted()
        Network.nearObjects.getNearObjects(currFormattedDate,Constants.API_KEY).execute().body()
    }
    // runs the fx --> suspends the fx -->

    // we get the nearObject
    // How to make a network call? in a different thread?

    // we turn it into a list ?
    //
    //


    /***/
    /*private  fun getCurrentDateFormatted(): String {

        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

        return dateFormat.format(currentTime)
    }*/
}