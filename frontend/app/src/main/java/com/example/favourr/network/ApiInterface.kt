package com.example.favourr.network

import com.example.favourr.models.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    @GET("bounties/city/{city_name}")
    fun getCityFavourrs(@Path("city_name") cityName: String) : Call<CityModel>

    @POST("bounties")
    fun postFavourr(@Body body: RequestBody) : Call<FavourrModel>

    @POST("users")
    fun postUser(@Body body: RequestBody) : Call<UserContainerModel>

    @PUT("bounties/{user_id}/{bounty_id}/{level}")
    fun updateFavourrProgress(
        @Path("user_id") userId: String,
        @Path("bounty_id") bountyId: String,
        @Path("level") level: Int
    ): Call<UpdatedFavourrModel>

    @GET("bounties/accepted/{user_id}")
    fun getAcceptedBounties(@Path("user_id") userId: String) : Call<ProfileModel>

    @GET("bounties/created/{user_id}")
    fun getCreatedBounties(@Path("user_id") userId: String) : Call<ProfileModel>

    companion object {
        private const val BASE_URL = "https://backend-rqj26lvvaa-uc.a.run.app/api/"
        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}