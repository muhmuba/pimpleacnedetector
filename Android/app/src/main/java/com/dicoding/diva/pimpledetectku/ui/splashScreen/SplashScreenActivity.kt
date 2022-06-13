package com.dicoding.diva.pimpledetectku.ui.splashScreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.dicoding.diva.pimpledetectku.databinding.ActivitySplashScreenBinding
import com.dicoding.diva.pimpledetectku.ui.welcome.WelcomeActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        playAnimation()

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    private fun playAnimation() {

        val logo = ObjectAnimator.ofFloat(binding.logo, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleApp, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(logo, title)
            startDelay = 500
        }.start()
    }
}