package com.retrofit
import com.tony_fire.cryptogenius.InfoClass
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitApi {


    @POST("google")
    fun signUpUser(@Body userInfo: UserInfo): Call<InfoClass>


}