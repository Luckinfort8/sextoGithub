package com.example.myapplication

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")  // Reemplaza con el endpoint de clasificación definido en tu servidor Flask
    fun uploadImage(@Part image: MultipartBody.Part): Call<ApiResponse>
}