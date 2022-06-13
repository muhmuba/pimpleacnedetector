package com.dicoding.diva.pimpledetectku.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.WindowInsets
import android.view.WindowManager
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.ViewModelFactory
import com.dicoding.diva.pimpledetectku.databinding.ActivityMainBinding
import com.dicoding.diva.pimpledetectku.model.UserModel
import com.dicoding.diva.pimpledetectku.model.UserPreference
import com.dicoding.diva.pimpledetectku.ui.daftarJerawat.DaftarJerawatActivity
import com.dicoding.diva.pimpledetectku.ui.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
//    private lateinit var user: UserModel
    private lateinit var mainViewModel: MainViewModel


    companion object {
        val EXTRA_DETAIL ="extra_detail"
        const val EXTRA_MAIN_TOKEN = "extra_main_token"
        const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
//        setupToken()
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        binding.apply {
//            setupToken()
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home,
                    R.id.nav_home_activity,
                    R.id.nav_daftar_jerawat,
//                R.id.nav_jenis_jerawat,
//                R.id.nav_solusi_jerawat,
                    R.id.nav_about
                ), drawerLayout
            )
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        mainViewModel.getUser().observe(this,{user->
//            Log.d(TAG,"isLogin: ${user.isLogin}")
//            if(user.isLogin){
//                Log.e(TAG,"Token: " + user.token)
//            }
//            else {
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                Log.d(TAG,"Token: ${user.token}")
            } else {
                startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
                finish()
            }
        }
    }

//    private fun setupToken(){
//        mainViewModel.getUser().observe(this,{ user ->
//            Intent(this,DaftarJerawatActivity::class.java).let {
//                it.putExtra(DaftarJerawatActivity.EXTRA_TOKEN,user.token)
//                startActivity(it)
//            }
//        })
//    }


}