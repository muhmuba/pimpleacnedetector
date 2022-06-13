package com.dicoding.diva.pimpledetectku.api

import com.dicoding.diva.pimpledetectku.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email : String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @GET("acnes")
    fun getListAcne(
        @Header("Authorization") token: String
    ) : Call<GetAcneList>

    @GET("acne")
    fun getResult(
        @Header("Authorization") token: String,
        @Query("type") type: String
    ) : Call<AcneItemsResult>
}

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = if(BuildConfig.DEBUG){
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
        const val BASE_URL = "http://34.134.27.72/api/"
        const val API_EMAIL = "api@mail.com"
        const val API_PASSWORD = "]4MZb@2#A:X8[sU'"
    }
}