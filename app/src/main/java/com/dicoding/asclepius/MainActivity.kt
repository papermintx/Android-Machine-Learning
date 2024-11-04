package com.dicoding.asclepius

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.view.MainViewApp
import com.dicoding.asclepius.view.history.AnalyzeHistoryFragment
import com.dicoding.asclepius.view.home.HomeFragment
import com.dicoding.asclepius.view.home.MainViewModel
import com.dicoding.asclepius.view.news.NewsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewApp by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
            mainViewModel.currentFragment = "Home"
            mainViewModel.bottomNavPosition = 0
        } else {
            binding.bottomNavigationView.selectedItemId = when (mainViewModel.bottomNavPosition) {
                0 -> R.id.navigation_home
                1 -> R.id.navigation_history
                2 -> R.id.navigation_news
                else -> R.id.navigation_home
            }
            loadFragment(getFragmentByName(mainViewModel.currentFragment))
        }

        val bottomNavigation: BottomNavigationView = binding.bottomNavigationView
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    mainViewModel.currentFragment = "Home"
                    mainViewModel.bottomNavPosition = 0
                    true
                }
                R.id.navigation_history -> {
                    loadFragment(AnalyzeHistoryFragment())
                    mainViewModel.currentFragment = "History"
                    mainViewModel.bottomNavPosition = 1
                    true
                }
                R.id.navigation_news -> {
                    loadFragment(NewsFragment())
                    mainViewModel.currentFragment = "News"
                    mainViewModel.bottomNavPosition = 2
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    private fun getFragmentByName(name: String?): Fragment {
        return when (name) {
            "Home" -> HomeFragment()
            "History" -> AnalyzeHistoryFragment()
            "News" -> NewsFragment()
            else -> HomeFragment()
        }
    }
}