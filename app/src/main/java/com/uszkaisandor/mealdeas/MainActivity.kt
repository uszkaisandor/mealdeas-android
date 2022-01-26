package com.uszkaisandor.mealdeas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.uszkaisandor.mealdeas.api.NewsApi
import com.uszkaisandor.mealdeas.data.AccountProperties
import com.uszkaisandor.mealdeas.databinding.ActivityMainBinding
import com.uszkaisandor.mealdeas.shared.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var selectedIndex = 0

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager.setAccountProperties(AccountProperties(token = NewsApi.ACCESS_KEY))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)
    }

    interface OnBottomNavigationFragmentReselectedListener {
        fun onBottomNavigationFragmentReselected()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_INDEX, selectedIndex)
    }
}

private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"