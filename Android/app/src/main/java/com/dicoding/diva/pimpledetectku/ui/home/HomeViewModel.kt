package com.dicoding.diva.pimpledetectku.ui.home

import androidx.lifecycle.*
import com.dicoding.diva.pimpledetectku.api.AcneItemsResult
import com.dicoding.diva.pimpledetectku.model.ResultModel
import com.dicoding.diva.pimpledetectku.model.ResultPreference
import com.dicoding.diva.pimpledetectku.model.UserModel
import com.dicoding.diva.pimpledetectku.model.UserPreference
import kotlinx.coroutines.launch

class HomeViewModel(private val pref : UserPreference) : ViewModel() {
    val listAcneResult = MutableLiveData<List<AcneItemsResult>>()

    val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
//    fun getResult(): LiveData<ResultModel> {
//        return prefResult.getResult().asLiveData()
//    }
//
//    fun result(result: ResultModel) {
//        viewModelScope.launch {
//            prefResult.result(result)
//        }
//    }

}