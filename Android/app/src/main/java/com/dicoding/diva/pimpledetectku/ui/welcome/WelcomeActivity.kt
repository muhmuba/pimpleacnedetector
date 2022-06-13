package com.dicoding.diva.pimpledetectku.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.diva.pimpledetectku.ui.main.MainActivity
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.ViewModelFactory
import com.dicoding.diva.pimpledetectku.model.UserModel
import com.dicoding.diva.pimpledetectku.api.ApiConfig
import com.dicoding.diva.pimpledetectku.api.ResponseLogin
import com.dicoding.diva.pimpledetectku.databinding.ActivityWelcomeBinding
import com.dicoding.diva.pimpledetectku.model.UserPreference
import com.dicoding.diva.pimpledetectku.ui.daftarJerawat.DaftarJerawatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var user: UserModel
    private lateinit var welcomeViewModel: WelcomeViewModel


    companion object{
        const val TAG = "WelcomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupView()
        setupAction()
        setupViewModel()


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
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        welcomeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[WelcomeViewModel::class.java]

        welcomeViewModel.getUser().observe(this, { user ->
            Log.d("Yeay Berhasil Login: ",user.token)
            this.user = user

//            Log.d(TAG,"Token: ${user.token}")
//            if(this.user.isLogin){
//                Intent(this@WelcomeActivity, MainActivity::class.java).let {
//                    it.putExtra(MainActivity.EXTRA_MAIN_TOKEN,user.token)
//                    startActivity(it)
//                    finish()
//                }
//            } else {
//                setupAction()
//            }
            Log.d("TOKEN:  ",user.token)
        })
    }

    private fun setupAction(){
        binding.startBtn.setOnClickListener {
            val email = ApiConfig.API_EMAIL
            val password = ApiConfig.API_PASSWORD
            postLogin(email, password)
            Log.d(TAG,"Token: ${user.token}")

            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_MAIN_TOKEN, user.token)
            startActivity(intent)
            finish()
//            intent.let {
//                it.putExtra(MainActivity.EXTRA_MAIN_TOKEN,user.token)
//                startActivity(it)
//                finish()
//            }
//            Log.d(TAG,"Token: ${user.token}")
//            startActivity(intent)
//            finish()
        }
    }

//    private fun login(){
//        val email = ApiConfig.API_EMAIL
//        val password = ApiConfig.API_PASSWORD
//        postLogin(email, password)
//    }

    private fun postLogin(email: String, password: String){
        showLoading(true)
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.d(TAG,"ResponseBodySuccess")
                    Log.d(TAG,"token: " + user.token)
                    val success = responseBody.success
                    if (success) {
                        val user = UserModel(
                            responseBody.data.token,
                            responseBody.data.name,
                            true
                        )
                        welcomeViewModel.login(user)
                        welcomeViewModel._isLoading.value = false
                        welcomeViewModel._message.value = responseBody.message
                        Log.e(TAG,"onSuccess: ${responseBody.message}")
                    } else {
                        welcomeViewModel._isLoading.value = false
                        welcomeViewModel._message.value = getString(R.string.login_gagal)
                    }
                } else {
                    welcomeViewModel._isLoading.value = false
                    welcomeViewModel._message.value = getString(R.string.salah_akun)
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                welcomeViewModel._isLoading.value = false
                welcomeViewModel._message.value = t.message
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}