package com.dicoding.diva.pimpledetectku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.diva.pimpledetectku.model.UserPreference
import com.dicoding.diva.pimpledetectku.ui.daftarJerawat.DaftarJerawatViewModel
import com.dicoding.diva.pimpledetectku.ui.main.MainViewModel
import com.dicoding.diva.pimpledetectku.ui.welcome.WelcomeViewModel

class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DaftarJerawatViewModel::class.java) -> {
                DaftarJerawatViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}