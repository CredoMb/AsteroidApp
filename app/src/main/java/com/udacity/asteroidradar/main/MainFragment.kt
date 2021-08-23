package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    // Here, the init is already performed
    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this,MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        // Why is the size empty ?

        viewModel.asteroids.observe(viewLifecycleOwner, Observer<List<Asteroid>>{
            asteroidList -> Log.i("eee pegzé : ",asteroidList.get(0).codename)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    /**
     * Click listener for asteroids. By giving the block a name it helps a reader understand what it does.
     *
     */
    class AsteroidClick(val block: (Asteroid) -> Unit) {
        /**
         * Called when a asteroid is clicked
         *
         * @param asteroid the asteroid that was clicked
         */
        fun onClick(asteroid: Asteroid) = block(asteroid)
    }
}
