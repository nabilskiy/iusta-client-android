package com.ls.iusta.remote

import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.data.models.info.AboutEntity
import com.ls.iusta.data.models.info.FaqEntity
import com.ls.iusta.data.models.info.TermsEntity
import com.ls.iusta.data.models.user.LoginEntity
import com.ls.iusta.data.models.user.UserEntity
import com.ls.iusta.data.repository.UserRemote
import com.ls.iusta.remote.api.UserService
import com.ls.iusta.remote.mappers.customer.CustomerEntityMapper
import com.ls.iusta.remote.mappers.info.AboutEntityMapper
import com.ls.iusta.remote.mappers.info.FaqEntityMapper
import com.ls.iusta.remote.mappers.info.TermsEntityMapper
import com.ls.iusta.remote.mappers.user.LoginEntityMapper
import com.ls.iusta.remote.mappers.user.UserEntityMapper
import javax.inject.Inject

class UserRemoteImpl @Inject constructor(
    private val userService: UserService,
    private val loginEntityMapper: LoginEntityMapper,
    private val userEntityMapper: UserEntityMapper,
    private val customerEntityMapper: CustomerEntityMapper,
    private val aboutEntityMapper: AboutEntityMapper,
    private val faqEntityMapper: FaqEntityMapper,
    private val termsEntityMapper: TermsEntityMapper
) : UserRemote {

    override suspend fun login(email: String, password: String): LoginEntity =
        loginEntityMapper.mapFromModel(userService.login(email, password))

    override suspend fun userInfo(authToken: String?): UserEntity =
        userEntityMapper.mapFromModel(userService.userInfo(authToken))


    override suspend fun customers(query: String): List<CustomerEntity> =
        userService.customers(query).response.map {
            customerEntityMapper.mapFromModel(it)
        }

    override suspend fun setAuthToken(authToken: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        username: String, password: String, password_confirmation: String, firstname: String,
        lastname: String, middlename: String, phone_number: String, birthday: String,
        email: String, il_customer_id: String, language: String
    ): Boolean =
        userService.registerUser(
            username,
            password,
            password_confirmation,
            firstname,
            lastname,
            middlename,
            phone_number,
            birthday,
            email,
            il_customer_id,
            language
        ).success

    override suspend fun editUserInfo(
        firstname: String,
        lastname: String,
        middlename: String,
        phone_number: String,
        birthday: String,
        email: String,
        il_customer_id: String,
        language: String,
        auth_token: String
    ): Boolean =
        userService.userInfoEdit(
            firstname,
            lastname,
            middlename,
            phone_number,
            birthday,
            email,
            il_customer_id,
            language,
            auth_token
        ).success


    override suspend fun resetPassword(email: String): Boolean =
        userService.passwordReset(email).success

    override suspend fun updatePassword(
        old_password: String,
        new_password: String,
        new_password_confirmation: String,
        auth_token: String
    ): Boolean =
        userService.passwordUpdate(
            old_password,
            new_password,
            new_password_confirmation,
            auth_token
        ).success

    override suspend fun logout(auth_token: String): Boolean =
        userService.logout(auth_token).success

    override suspend fun about(auth_token: String): List<AboutEntity> =
        userService.about(auth_token).response.map {
            aboutEntityMapper.mapFromModel(it)
        }


    override suspend fun faq(lang: String?, auth_token: String): List<FaqEntity> =
        userService.faq(lang, auth_token).response.map {
            faqEntityMapper.mapFromModel(it)
        }


    override suspend fun terms(lang: String?, auth_token: String): List<TermsEntity> =
        userService.terms(lang, auth_token).response.map {
            termsEntityMapper.mapFromModel(it)
        }

}