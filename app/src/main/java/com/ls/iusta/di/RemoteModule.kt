package com.ls.iusta.di

import com.ls.iusta.BuildConfig
import com.ls.iusta.data.repository.TicketRemote
import com.ls.iusta.remote.TicketRemoteImpl
import com.ls.iusta.remote.api.TicketService
import com.ls.iusta.remote.api.ServiceFactory
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
        ServiceFactory.create(BuildConfig.DEBUG, BuildConfig.BASE_URL)

    @Singleton
    @Provides
    fun provideTicketRemote(ticketRemoteImpl: TicketRemoteImpl): TicketRemote =
        ticketRemoteImpl
}