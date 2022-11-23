package com.ls.iusta.di

import com.ls.iusta.BuildConfig
import com.ls.iusta.data.repository.TicketRemote
import com.ls.iusta.data.repository.UserRemote
import com.ls.iusta.remote.TicketRemoteImpl
import com.ls.iusta.remote.UserRemoteImpl
import com.ls.iusta.remote.api.TicketService
import com.ls.iusta.remote.api.ServiceFactory
import com.ls.iusta.remote.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideTicketService(): TicketService =
        ServiceFactory.createTicketService(BuildConfig.DEBUG, BuildConfig.BASE_URL)

    @Singleton
    @Provides
    fun provideUserService(): UserService =
        ServiceFactory.createUserService(BuildConfig.DEBUG, BuildConfig.BASE_URL)

    @Singleton
    @Provides
    fun provideTicketRemote(ticketRemoteImpl: TicketRemoteImpl): TicketRemote =
        ticketRemoteImpl

    @Singleton
    @Provides
    fun provideUserRemote(userRemoteImpl: UserRemoteImpl): UserRemote =
        userRemoteImpl



}