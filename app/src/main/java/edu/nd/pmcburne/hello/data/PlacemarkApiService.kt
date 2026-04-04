package edu.nd.pmcburne.hello.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PlacemarkApiService {

    @GET("placemarks.json")
    suspend fun getPlacemarks(): List<PlacemarkResponse>

    companion object {
        private const val BASE_URL = "https://www.cs.virginia.edu/~kmp3xr/"

        fun create(): PlacemarkApiService =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlacemarkApiService::class.java)
    }
}