package com.ls.iusta.data

import com.ls.iusta.data.mapper.customer.CustomerMapper
import com.ls.iusta.data.mapper.customer.CustomerResponseMapper
import com.ls.iusta.data.mapper.info.AboutMapper
import com.ls.iusta.data.mapper.info.FaqMapper
import com.ls.iusta.data.mapper.info.TermsMapper
import com.ls.iusta.data.mapper.push.PushMapper
import com.ls.iusta.data.mapper.user.BaseMapper
import com.ls.iusta.data.mapper.user.LoginMapper
import com.ls.iusta.data.mapper.user.UserMapper
import com.ls.iusta.data.source.UserDataSourceFactory
import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.customer.CustomerResponse
import com.ls.iusta.domain.models.info.About
import com.ls.iusta.domain.models.info.Faq
import com.ls.iusta.domain.models.info.Terms
import com.ls.iusta.domain.models.push.Push
import com.ls.iusta.domain.models.user.Base
import com.ls.iusta.domain.models.user.User
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserRepositoryImpl @Inject constructor(
    private val userDataSourceFactory: UserDataSourceFactory,
    private val loginMapper: LoginMapper,
    private val userMapper: UserMapper,
    private val customerResponseMapper: CustomerResponseMapper,
    private val aboutMapper: AboutMapper,
    private val faqMapper: FaqMapper,
    private val termsMapper: TermsMapper,
    private val pushMapper: PushMapper,
    private val baseMapper: BaseMapper
) : UserRepository {

    override suspend fun login(email: String, password: String): Flow<Login> = flow {
        var login = userDataSourceFactory.getRemoteDataSource().login(email, password)
        userDataSourceFactory.getCacheDataSource().setAuthToken(login.auth_token)
        emit(loginMapper.mapFromEntity(login))
    }

    override suspend fun register(
        username: String,
        password: String,
        password_confirmation: String,
        firstname: String,
        lastname: String,
        middlename: String,
        phone_number: String,
        birthday: String,
        email: String,
        il_customer_id: String,
        language: String
    ): Flow<Base> = flow {
        var created = userDataSourceFactory.getRemoteDataSource().register(
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
        )
        emit(baseMapper.mapFromEntity(created))
    }

    override suspend fun userInfo(): Flow<User> = flow {
        var user = userDataSourceFactory.getRemoteDataSource()
            .userInfo(userDataSourceFactory.getAuthToken())
        emit(userMapper.mapFromEntity(user))
    }

    override suspend fun isUserLogged(): Flow<Boolean?> = flow {
        var isLogged = userDataSourceFactory.getAuthToken()?.isNotEmpty()
        emit(isLogged)
    }

    override suspend fun getAuthToken(): Flow<String?> = flow {
        var token = userDataSourceFactory.getAuthToken()
        emit(token)
    }

    override suspend fun customers(query: String): Flow<CustomerResponse> = flow {
        val customers = userDataSourceFactory.getRemoteDataSource().customers(query)
        emit(customerResponseMapper.mapFromEntity(customers))
    }

    override suspend fun editUserInfo(
        firstname: String,
        lastname: String,
        middlename: String?,
        phone_number: String,
        birthday: String,
        email: String,
        il_customer_id: String,
        language: String
    ): Flow<Boolean> = flow {
        val edited = userDataSourceFactory.getRemoteDataSource().editUserInfo(
            firstname,
            lastname,
            middlename,
            phone_number,
            birthday,
            email,
            il_customer_id,
            language,
            userDataSourceFactory.getAuthToken()
        )
        emit(edited)
    }

    override suspend fun resetPassword(email: String): Flow<Boolean> = flow {
        val reset = userDataSourceFactory.getRemoteDataSource().resetPassword(email)
        emit(reset)
    }

    override suspend fun updatePassword(
        old_password: String,
        new_password: String,
        new_password_confirmation: String
    ): Flow<Boolean> = flow {
        val updated = userDataSourceFactory.getRemoteDataSource()
            .updatePassword(
                old_password,
                new_password,
                new_password_confirmation,
                userDataSourceFactory.getAuthToken()
            )
        emit(updated)
    }

    override suspend fun logout(): Flow<Boolean> = flow {
        val logout =
            userDataSourceFactory.getRemoteDataSource().logout(userDataSourceFactory.getAuthToken())
        userDataSourceFactory.getCacheDataSource().setAuthToken("")
        emit(logout)
    }

    override suspend fun logoutForce(): Flow<Boolean> = flow {
        userDataSourceFactory.getCacheDataSource().setAuthToken("")
        emit(true)
    }

    override suspend fun about(): Flow<List<About>> = flow {
        val aboutList =
            userDataSourceFactory.getRemoteDataSource().about(userDataSourceFactory.getAuthToken())
                .map { aboutEntity ->
                    aboutMapper.mapFromEntity(aboutEntity)
                }
        emit(aboutList)
    }

    override suspend fun faq(lang: String?): Flow<List<Faq>> = flow {
        val faqList = userDataSourceFactory.getRemoteDataSource()
            .faq(lang, userDataSourceFactory.getAuthToken())
            .map { faqEntity ->
                faqMapper.mapFromEntity(faqEntity)
            }
        emit(faqList)
    }

    override suspend fun terms(lang: String?): Flow<List<Terms>> = flow {
        val termsList = userDataSourceFactory.getRemoteDataSource().terms(lang)
            .map { termsEntity ->
                termsMapper.mapFromEntity(termsEntity)
            }
        emit(termsList)
    }

    override suspend fun savePushToken(push_token: String): Flow<Boolean> = flow {
        val result = userDataSourceFactory.getRemoteDataSource()
            .savePushToken(push_token, userDataSourceFactory.getAuthToken())
        emit(result)
    }

    override suspend fun notifications(): Flow<List<Push>> = flow {
        val notifications = userDataSourceFactory.getRemoteDataSource()
            .notifications(userDataSourceFactory.getAuthToken()).map { pushEntity ->
                pushMapper.mapFromEntity(pushEntity)
            }
        emit(notifications)
    }

    override suspend fun readPush(ids: String): Flow<Boolean> = flow {
        val result = userDataSourceFactory.getRemoteDataSource()
            .readPush(ids, userDataSourceFactory.getAuthToken())
        emit(result)
    }

    override suspend fun deletePush(ids: String): Flow<Boolean> = flow {
        val result = userDataSourceFactory.getRemoteDataSource()
            .deletePush(ids, userDataSourceFactory.getAuthToken())
        emit(result)
    }
}
