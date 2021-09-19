package com.example.favourr.network

import com.example.favourr.models.CityModel
import com.example.favourr.models.FavourrModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @GET("city/{city_name}")
    fun getCityFavourrs(@Path("city_name") cityName: String) : Call<CityModel>

    @POST(".")
    fun postFavourr(@Body body: RequestBody) : Call<FavourrModel>

    companion object {
        private const val BASE_URL = "https://backend-rqj26lvvaa-uc.a.run.app/api/bounties/"
        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}