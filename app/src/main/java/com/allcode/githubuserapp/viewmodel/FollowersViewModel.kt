package com.allcode.githubuserapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.allcode.githubuserapp.Utils
import com.allcode.githubuserapp.Utils.Companion.TOKEN
import com.allcode.githubuserapp.model.SimpleUser
import com.allcode.githubuserapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _followers = MutableLiveData<ArrayList<SimpleUser>?>(null)
    val followers: LiveData<ArrayList<SimpleUser>?> = _followers

    fun getUserFollowers(username: String) {
        _isLoading.value = true

        ApiConfig.getApiService().getUserFollowers(token = "token $TOKEN", username)
            .apply {
                enqueue(object : Callback<ArrayList<SimpleUser>> {
                    override fun onResponse(
                        call: Call<ArrayList<SimpleUser>>,
                        response: Response<ArrayList<SimpleUser>>
                    ) {
                        if (response.isSuccessful) _followers.value = response.body()
                        else Log.e(TAG, response.message())
                        _isLoading.value = false
                    }

                    override fun onFailure(call: Call<ArrayList<SimpleUser>>, t: Throwable) {
                        Log.e(TAG, t.message.toString())

                        _followers.value = arrayListOf()
                        _isLoading.value = false
                    }

                })
            }
    }

    companion object {
        private val TAG = FollowersViewModel::class.java.simpleName
    }

}