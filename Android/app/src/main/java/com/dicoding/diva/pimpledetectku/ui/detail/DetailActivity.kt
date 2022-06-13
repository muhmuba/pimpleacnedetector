package com.dicoding.diva.pimpledetectku.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dicoding.diva.pimpledetectku.R
import com.dicoding.diva.pimpledetectku.api.AcneItems
import com.dicoding.diva.pimpledetectku.databinding.ActivityDetailBinding
import com.dicoding.diva.pimpledetectku.ui.main.MainActivity


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val item = intent.getParcelableExtra<AcneItems>(MainActivity.EXTRA_DETAIL)
//        val image = findViewById<ImageView>(R.id.imageView)
//        val name = findViewById<TextView>(R.id.name_tv)
//        val description = findViewById<TextView>(R.id.description_tv)
//        val cause = findViewById<TextView>(R.id.cause_tv)
//        val solution = findViewById<TextView>(R.id.solution_tv)
//
//        image.setImageResource(item?.image)
//
//        supportActionBar?.title = item.name
        setupData()
    }

    private fun setupData() {
        val item = intent.getParcelableExtra<AcneItems>("AcneItems") as AcneItems
        Glide.with(applicationContext)
            .load(item.image)
            .centerCrop()
            .into(findViewById(R.id.imageView))
        findViewById<TextView>(R.id.name_tv).text = item.name
        findViewById<TextView>(R.id.description_tv).text = item.description
        findViewById<TextView>(R.id.cause_tv).text = item.cause
        findViewById<TextView>(R.id.solution_tv).text = item.solution


//        binding.nameTv.text = item.name
//        binding.descriptionTv.text = item.description
//        binding.causeTv.text = item.cause
//        binding.solutionTv.text = item.solution
        supportActionBar?.title = item.name

    }
}