package com.dicoding.diva.pimpledetectku.ui.solusiJerawat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.databinding.ActivitySolusiJerawatBinding

class SolusiJerawatActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySolusiJerawatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolusiJerawatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.menu_solusi)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}