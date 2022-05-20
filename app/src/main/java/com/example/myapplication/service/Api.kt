package com.example.myapplication.service

import com.example.myapplication.model.ListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {

    @GET("repositories?q=since=daily")
    fun getAllRepository(): Call<ListResponse?>
}