package com.dicoding.diva.pimpledetectku.ui.daftarJerawat

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.ViewModelFactory
import com.dicoding.diva.pimpledetectku.adapter.ListAcneAdapter
import com.dicoding.diva.pimpledetectku.api.AcneItems
import com.dicoding.diva.pimpledetectku.databinding.ActivityDaftarJerawatBinding
import com.dicoding.diva.pimpledetectku.model.UserPreference
import com.dicoding.diva.pimpledetectku.ui.detail.DetailActivity
import com.dicoding.diva.pimpledetectku.ui.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DaftarJerawatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarJerawatBinding
    private lateinit var daftarJerawatViewModel: DaftarJerawatViewModel
//    private lateinit var token: String

    companion object {
        const val EXTRA_TOKEN = "extra_token"
        const val TAG = "DaftarJerawatActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarJerawatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()

        daftarJerawatViewModel.listAcnes.observe(this, {
            setupAction(it)
            showLoading(false)
        })

        daftarJerawatViewModel.getUser().observe(this,{ user->
            if(user.isLogin){
                Log.d(TAG,"Token: ${user.token}")
                user.token.let { daftarJerawatViewModel.getAcnesList(user.token) }
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            Log.d(TAG,"isLogin: ${user.isLogin}")
        })


        daftarJerawatViewModel.isLoading.observe(this,{
            showLoading(true)
        })

        daftarJerawatViewModel.message.observe(this,{
            Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
        })

    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
        supportActionBar?.title = "Daftar Jerawat"
    }

    private fun setupAction(listAcnes: ArrayList<AcneItems>) {
        val adapter = ListAcneAdapter(listAcnes)
        binding.apply {
            acnesRv.layoutManager = LinearLayoutManager(this@DaftarJerawatActivity)
            acnesRv.setHasFixedSize(true)
            acnesRv.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : ListAcneAdapter.OnItemClickCallback {
            override fun onItemClicked(data: AcneItems) {
                val intentToDetail = Intent(this@DaftarJerawatActivity, DetailActivity::class.java)
                intentToDetail.putExtra("AcneItems", data)
                startActivity(intentToDetail)
                showSelectedAcne(data)
            }

        })
    }

    private fun showSelectedAcne(acne: AcneItems){
        Toast.makeText(this, "Detail Dari " + acne.name, Toast.LENGTH_SHORT).show()
    }

    private fun setupViewModel() {
        daftarJerawatViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))).get(DaftarJerawatViewModel::class.java)

        daftarJerawatViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                Log.d(TAG,"Token: ${user.token}")
                val actionBar = supportActionBar
                actionBar!!.title = getString(R.string.menu_daftar)
                actionBar.setDisplayHomeAsUpEnabled(true)
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            Log.d(TAG,"isLogin: ${user.isLogin}")
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}