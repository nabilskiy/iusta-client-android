package com.ls.iusta.cache.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ls.iusta.cache.dao.TicketDao
import com.ls.iusta.cache.dao.UserDao
import com.ls.iusta.cache.models.LoginCacheEntity
import com.ls.iusta.cache.models.TicketCacheEntity
import com.ls.iusta.cache.utils.Migrations
import com.ls.iusta.cache.utils.CacheConstants
import javax.inject.Inject

@Database(
    entities = [TicketCacheEntity::class, LoginCacheEntity::class],
    version = Migrations.DB_VERSION,
    exportSchema = false
)
abstract class TicketsDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedTicketDao(): TicketDao
    abstract fun cachedUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: TicketsDatabase? = null

        fun getInstance(context: Context): TicketsDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TicketsDatabase::class.java,
            CacheConstants.DB_NAME
        ).build()
    }
}
