package com.ls.iusta.remote.api

import com.ls.iusta.remote.models.*
import com.ls.iusta.remote.models.about.AboutResponseModel
import com.ls.iusta.remote.models.customer.CustomerResponseModel
import com.ls.iusta.remote.models.faq.FaqResponseModel
import com.ls.iusta.remote.models.terms.TermsResponseModel
import com.ls.iusta.remote.models.user.LoginModel
import com.ls.iusta.remote.models.user.UserModel
import retrofit2.http.*

interface UserService {

    @FormUrlEncoded
    @POST(Endpoints.USER_LOGIN)
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): LoginModel

    @FormUrlEncoded
    @POST(Endpoints.USER_RESET_PASSWORD)
    suspend fun passwordReset(
        @Field("email") email: String,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): BaseModel

    @FormUrlEncoded
    @POST(Endpoints.USER_UPDATE_PASSWORD)
    suspend fun passwordUpdate(
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String,
        @Field("new_password_confirmation") new_password_confirmation: String,
        @Field("auth_token") auth_token: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): BaseModel

    @FormUrlEncoded
    @POST(Endpoints.USER_LOGOUT)
    suspend fun logout(
        @Field("auth_token") auth_token: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): BaseModel

    @FormUrlEncoded
    @POST(Endpoints.USER_INFO)
    suspend fun userInfo(
        @Field("auth_token") auth_token: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): UserModel

    @FormUrlEncoded
    @POST(Endpoints.USER_INFO_ADD)
    suspend fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("middlename") middlename: String,
        @Field("phone_number") phone_number: String,
        @Field("birthday") birthday: String,
        @Field("email") email: String,
        @Field("il_customer_id") il_customer_id: String,
        @Field("language") language: String,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): BaseModel

    @FormUrlEncoded
    @POST(Endpoints.USER_INFO_EDIT)
    suspend fun userInfoEdit(
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("middlename") middlename: String?,
        @Field("phone_number") phone_number: String,
        @Field("birthday") birthday: String,
        @Field("email") email: String,
        @Field("Il_customer_id") Il_customer_id: String,
        @Field("language") language: String,
        @Field("auth_token") auth_token: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): BaseModel


    @FormUrlEncoded
    @POST(Endpoints.CUSTOMERS)
    suspend fun customers(
        @Field("query") query: String,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): CustomerResponseModel


    @GET(Endpoints.INFO_ABOUT)
    suspend fun about(
        @Query("auth_token") auth_token: String?,
        @Query("secret_key") secret_key: String = SECRET_KEY
    ): AboutResponseModel

    @GET(Endpoints.INFO_FAQ)
    suspend fun faq(
        @Query("lang") lang: String?,
        @Query("auth_token") auth_token: String?,
        @Query("secret_key") secret_key: String = SECRET_KEY
    ): FaqResponseModel

    @GET(Endpoints.INFO_DOCS)
    suspend fun docs(
        @Query("lang") lang: String?,
        @Query("secret_key") secret_key: String = SECRET_KEY
    ): TermsResponseModel

    companion object {
        private const val SECRET_KEY = "oLOk6x4FC1YCBctFTtEzhkHhkZFYJeW5xFlPZccC4mSmI5Ji"
    }


}
