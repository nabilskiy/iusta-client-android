package com.ls.iusta.di

import android.content.Context
import com.ls.iusta.cache.datasourceImpl.TicketCacheImpl
import com.ls.iusta.cache.dao.TicketDao
import com.ls.iusta.cache.dao.UserDao
import com.ls.iusta.cache.database.TicketsDatabase
import com.ls.iusta.cache.datasourceImpl.UserCacheImpl
import com.ls.iusta.cache.utils.CachePreferencesHelper
import com.ls.iusta.data.repository.TicketCache
import com.ls.iusta.data.repository.UserCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): TicketsDatabase {
        return TicketsDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideTicketDao(ticketsDatabase: TicketsDatabase): TicketDao {
        return ticketsDatabase.cachedTicketDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(ticketsDatabase: TicketsDatabase): UserDao {
        return ticketsDatabase.cachedUserDao()
    }


    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): CachePreferencesHelper {
        return CachePreferencesHelper(context)
    }

    @Provides
    @Singleton
    fun provideTicketCache(ticketCacheImpl: TicketCacheImpl): TicketCache =
        ticketCacheImpl

    @Provides
    @Singleton
    fun provideUserCache(userCacheImpl: UserCacheImpl): UserCache =
        userCacheImpl
}
