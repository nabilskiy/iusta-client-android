package com.ls.iusta.di

import com.ls.iusta.BuildConfig
import com.ls.iusta.data.TicketRepositoryImpl
import com.ls.iusta.data.SettingsRepositoryImpl
import com.ls.iusta.data.UserRepositoryImpl
import com.ls.iusta.domain.repository.TicketRepository
import com.ls.iusta.domain.repository.SettingsRepository
import com.ls.iusta.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(): SettingsRepository =
        SettingsRepositoryImpl(BuildConfig.VERSION_NAME)

    @Singleton
    @Provides
    fun provideTicketRepository(
        ticketRepositoryImpl: TicketRepositoryImpl
    ): TicketRepository = ticketRepositoryImpl

    @Singleton
    @Provides
    fun provideUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository = userRepositoryImpl
}