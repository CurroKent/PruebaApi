package com.example.marsphotos.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET


// Base URL for the Mars API
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

// Creating a Retrofit instance for making API requests
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

// Interface defining the API service with a method to get photos
interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

// Singleton object to encapsulate the API service creation using Retrofit
object MarsApi {
    // Lazy-initialized Retrofit service for accessing the Mars API
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}