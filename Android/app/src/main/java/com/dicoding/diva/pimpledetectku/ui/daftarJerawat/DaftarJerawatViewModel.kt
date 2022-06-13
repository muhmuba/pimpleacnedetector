package com.dicoding.diva.pimpledetectku.ui.daftarJerawat

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.diva.pimpledetectku.api.AcneItems
import com.dicoding.diva.pimpledetectku.api.ApiConfig
import com.dicoding.diva.pimpledetectku.api.GetAcneList
import com.dicoding.diva.pimpledetectku.model.UserModel
import com.dicoding.diva.pimpledetectku.model.UserPreference
import com.dicoding.diva.pimpledetectku.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DaftarJerawatViewModel(private val pref: UserPreference) : ViewModel() {
    val listAcnes = MutableLiveData<ArrayList<AcneItems>>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAcnesList(token: String){
        val listAcne = ArrayList<AcneItems>()
        val client = ApiConfig.getApiService().getListAcne("Bearer " + token)

        _isLoading.value = true
        client.enqueue(object : Callback<GetAcneList> {
            override fun onResponse(call: Call<GetAcneList>, response: Response<GetAcneList>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    Log.d(MainActivity.TAG, "Token: " + token)
                    listAcnes.postValue(responseBody.data)
                    listAcnes.value = listAcne
                } else{
                    Log.d(MainActivity.TAG, "Token: " + token)
                    _isLoading.value = false
                    _message.value = responseBody?.message
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GetAcneList>, t: Throwable) {
                Log.d(MainActivity.TAG, "Token: " + token)
                _isLoading.value = false
                _message.value = t.message
                Log.e(MainActivity.TAG,"onFailure: ${t.message.toString()}")
            }

        })

    }
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
}

