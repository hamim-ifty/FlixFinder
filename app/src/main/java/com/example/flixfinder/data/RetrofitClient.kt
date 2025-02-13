package com.example.flixfinder.data.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NWM3YzkxMjUxMzlhMjA3ZmQwZGUxMjJlZmE1ZjYyMSIsIm5iZiI6MTczODA2MjAxNy45NTcsInN1YiI6IjY3OThiOGMxYjkyMWM1NDhkODJhZjJjOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.d8k-ScWUrlqM7_rTR8XCrW55CEgALsCTC6DdEoInvUE"

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("API", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("accept", "application/json")
                .header("Authorization", "Bearer $API_KEY")
                .method(original.method, original.body)
                .build()

            Log.d("API", "Making request to: ${request.url}")
            Log.d("API", "Headers: ${request.headers}")

            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor)
        .build()

    val api: MovieApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MovieApi::class.java)
}