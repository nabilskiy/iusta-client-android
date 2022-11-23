package com.ls.iusta.remote.api

import com.ls.iusta.remote.models.LoginModel
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @FormUrlEncoded
    @POST("mobile/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("secret_key") secret_key: String
    ): LoginModel
}
