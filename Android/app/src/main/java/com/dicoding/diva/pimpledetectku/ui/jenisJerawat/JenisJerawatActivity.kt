package com.dicoding.diva.pimpledetectku.ui.jenisJerawat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.databinding.ActivityJenisJerawatBinding

class JenisJerawatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJenisJerawatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJenisJerawatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.menu_jenis)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}