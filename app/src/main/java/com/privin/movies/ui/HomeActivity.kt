package com.privin.movies.ui

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.privin.movies.R
import com.privin.movies.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: ActivityHomeBinding

    private lateinit var currentFragment: MovieListFragment

    private val nowPlayingFragment = NowPlayingFragment()
    private val popularMoviesFragment = PopularMoviesFragment()
    private val upComingMoviesFragment = UpComingMoviesFragment()
    private val topRatedMoviesFragment = TopRatedMoviesFragment()

    companion object {
        private const val NOW_PLAYING_TAG = "now"
        private const val POPULAR_MOVIES_TAG = "popular"
        private const val UPCOMING_MOVIES_TAG = "upcoming"
        private const val TOP_RATED_MOVIES_TAG = "top_rated"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        setContentView(binding.root)
        setCurrentFragment(nowPlayingFragment, NOW_PLAYING_TAG)
        setListeners()
        val titleText = getString(R.string.app_name)
        title = Html.fromHtml("<font color='#000000'> $titleText </font>", 0)
    }

    private fun setListeners() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.now_playing -> navigateTo(nowPlayingFragment, NOW_PLAYING_TAG)
                R.id.popular -> navigateTo(popularMoviesFragment, POPULAR_MOVIES_TAG)
                R.id.upcoming -> navigateTo(upComingMoviesFragment, UPCOMING_MOVIES_TAG)
                R.id.top_rated -> navigateTo(topRatedMoviesFragment, TOP_RATED_MOVIES_TAG)
                else -> {}
            }

            return@setOnItemSelectedListener true
        }
        binding.bottomNavigationView.setOnItemReselectedListener {
            currentFragment.scrollToTop()
        }
    }

    private fun navigateTo(fragment: MovieListFragment, tag: String) {
        if (currentFragment == fragment) return
        val ft = supportFragmentManager.beginTransaction()
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            ft.add(binding.container.id, fragment, tag)
        }
        ft.show(fragment)
        ft.hide(currentFragment)
        currentFragment = fragment
        ft.commit()
    }

    private fun setCurrentFragment(fragment: MovieListFragment, tag: String) {
        currentFragment = nowPlayingFragment
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(binding.container.id, fragment, tag)
        ft.commit()
    }
}