package com.dicoding.diva.pimpledetectku.ui.welcome


import androidx.lifecycle.*
import com.dicoding.diva.pimpledetectku.model.UserModel
import com.dicoding.diva.pimpledetectku.model.UserPreference
import kotlinx.coroutines.launch

class WelcomeViewModel(private val pref: UserPreference) : ViewModel() {
    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
//
    fun login(user: UserModel) {
        viewModelScope.launch {
            pref.login(user)
        }
    }

}