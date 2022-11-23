package com.ls.iusta.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ls.iusta.cache.datasourceImpl.UserCacheImpl
import com.ls.iusta.cache.models.LoginCacheEntity
import com.ls.iusta.cache.models.TicketCacheEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getLoginData(): LoginCacheEntity


    @Query("DELETE FROM user")
    fun clearUser(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLogin(vararg user: LoginCacheEntity) /* Accept either single item or list*/
}
