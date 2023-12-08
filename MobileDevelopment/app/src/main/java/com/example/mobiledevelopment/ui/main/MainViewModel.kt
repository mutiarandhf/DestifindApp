package com.example.mobiledevelopment.ui.main

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobiledevelopment.data.UserRepository
import com.example.mobiledevelopment.data.pref.UserModel
import com.example.mobiledevelopment.data.response.DestinationResponse
import com.example.mobiledevelopment.data.response.ListDestinationItem
import com.example.mobiledevelopment.data.response.LoginResult
import com.example.mobiledevelopment.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _listDst = MutableLiveData<List<ListDestinationItem>>()
    val listDst :LiveData<List<ListDestinationItem>> = _listDst

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<LoginResult> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    init {
        getSession()
    }

    @SuppressLint("SuspiciousIndentation")
    fun findUsers(token : String)  {
        _isLoading.value = true
        val client = ApiConfig.getApiService(token).getStories()
        client.enqueue(object : Callback<DestinationResponse> {
            override fun onResponse(call: Call<DestinationResponse>, response: Response<DestinationResponse>) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listDst.value = response.body()?.listStory
                }else{
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DestinationResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }
}