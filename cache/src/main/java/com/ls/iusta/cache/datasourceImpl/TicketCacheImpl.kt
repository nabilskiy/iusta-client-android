package com.ls.iusta.cache.datasourceImpl

import com.ls.iusta.cache.dao.TicketDao
import com.ls.iusta.cache.mapper.TicketCacheMapper
import com.ls.iusta.cache.utils.CachePreferencesHelper
import com.ls.iusta.data.repository.TicketCache
import com.ls.iusta.data.models.TicketEntity
import javax.inject.Inject

class TicketCacheImpl @Inject constructor(
    private val ticketDao: TicketDao,
    private val ticketCacheMapper: TicketCacheMapper,
    private val cachePreferencesHelper: CachePreferencesHelper
) : TicketCache {
    override suspend fun getTickets(): List<TicketEntity> =
        ticketDao.getTickets().map { cacheTicket ->
            ticketCacheMapper.mapFromCached(cacheTicket)
        }

    override suspend fun getTicket(ticketId: Long): TicketEntity =
        ticketCacheMapper.mapFromCached(ticketDao.getTicket(ticketId))

    override suspend fun saveTickets(listTickets: List<TicketEntity>) =
        ticketDao.addTicket(
            *listTickets.map {
                ticketCacheMapper.mapToCached(it)
            }.toTypedArray()
        )

    override suspend fun getBookMarkedTickets(): List<TicketEntity> =
        ticketDao.getBookMarkedTickets().map { cacheTicket ->
            ticketCacheMapper.mapFromCached(cacheTicket)
        }

    override suspend fun setTicketBookmarked(ticketId: Long): Int =
        ticketDao.bookmarkTicket(ticketId)

    override suspend fun setTicketUnBookMarked(ticketId: Long): Int =
        ticketDao.unBookmarkTicket(ticketId)

    override suspend fun isCached(): Boolean =
        ticketDao.getTickets().isNotEmpty()

    override suspend fun setLastCacheTime(lastCache: Long) {
        cachePreferencesHelper.lastCacheTime = lastCache
    }

    override suspend fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return cachePreferencesHelper.lastCacheTime
    }

    companion object {
        /**
         * Expiration time set to 5 minutes
         */
        const val EXPIRATION_TIME = (60 * 5 * 1000).toLong()
    }
}