package com.aditya.porfiliohandler.presenter.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aditya.porfiliohandler.data.datasource.UserDataSource
import com.aditya.porfiliohandler.data.local.SessionManager
import com.aditya.porfiliohandler.data.repository.UserRepositoryImpl
import com.aditya.porfiliohandler.databinding.ActivityMainBinding
import com.aditya.porfiliohandler.presenter.ui.login.LoginActivity
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModel
import com.aditya.porfiliohandler.presenter.viewmodel.MainViewModelFactory
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.aditya.porfiliohandler.R

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityMainBinding

    private val mSessionManager by lazy { SessionManager(this) }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            UserRepositoryImpl(
                UserDataSource(FirebaseFirestore.getInstance())
            )
        )
    }

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            Log.d(TAG, "POST_NOTIFICATIONS permission granted: $granted")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        drawerLayout = binding.drawerLayout
        navView = binding.navigationView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardFragment, R.id.aboutFragment, R.id.experienceFragment,
                R.id.projectsFragment, R.id.messagesFragment, R.id.blogsFragment
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val userId = intent.getStringExtra("USER_ID")
        val userEmail = intent.getStringExtra("USER_EMAIL")

        if (mSessionManager.isLoggedIn()) {
            mSessionManager.saveUserSession(userId ?: "", userEmail ?: "")
        } else {
            mSessionManager.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        regainData()
        setDrawer()
        subscribeToFCMTopic()
        requestNotificationPermission()
        handleNotificationDeepLink(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNotificationDeepLink(intent)
    }

    private fun handleNotificationDeepLink(intent: Intent?) {
        if (intent?.getStringExtra("navigate_to") == "messages") {
            navController.navigate(R.id.messagesFragment)
            intent.removeExtra("navigate_to")
        }
    }

    private fun subscribeToFCMTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("new_messages")
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Subscribed to new_messages topic")
                } else {
                    Log.e(TAG, "Failed to subscribe to new_messages topic", task.exception)
                }
            }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun setDrawer() {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    mSessionManager.clearSession()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_refresh -> {
                    regainData()
                    drawerLayout.closeDrawers()
                    true
                }
                else -> {
                    val handled = NavigationUI.onNavDestinationSelected(menuItem, navController)
                    if (handled) {
                        drawerLayout.closeDrawers()
                    }
                    handled
                }
            }
        }
    }

    private fun regainData() {
        viewModel.getDashboardData()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}