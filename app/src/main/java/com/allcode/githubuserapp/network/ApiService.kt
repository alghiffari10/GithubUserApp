package com.allcode.githubuserapp.network


import com.allcode.githubuserapp.model.ResponseSearch
import com.allcode.githubuserapp.model.SimpleUser
import com.allcode.githubuserapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("search/users")
    fun searchUsername(
        @Header("Authorization") token: String,
        @Query("q") q: String
    ): Call<ResponseSearch>


    @GET("users/{username}")
    fun getUserDetail(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<User>


    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<ArrayList<SimpleUser>>



    @GET("users/{username}/following")
    fun getUserFollowing(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ): Call<ArrayList<SimpleUser>>



}