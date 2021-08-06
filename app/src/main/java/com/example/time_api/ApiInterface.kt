package com.example.time_api

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("Algiers")
    fun get_time():Call<Timing>
}