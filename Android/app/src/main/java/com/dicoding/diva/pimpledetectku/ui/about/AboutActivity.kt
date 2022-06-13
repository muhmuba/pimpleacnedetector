package com.dicoding.diva.pimpledetectku.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.menu_about)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}