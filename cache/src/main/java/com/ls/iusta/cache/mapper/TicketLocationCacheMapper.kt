package com.ls.iusta.cache.mapper

import com.ls.iusta.cache.models.TicketLocationCacheEntity
import com.ls.iusta.data.models.TicketLocationEntity
import javax.inject.Inject

class TicketLocationCacheMapper @Inject constructor() :
    CacheMapper<TicketLocationCacheEntity, TicketLocationEntity> {

    override fun mapFromCached(type: TicketLocationCacheEntity): TicketLocationEntity {
        return TicketLocationEntity(name = type.name, url = type.url)
    }

    override fun mapToCached(type: TicketLocationEntity): TicketLocationCacheEntity {
        return TicketLocationCacheEntity(name = type.name, url = type.url)
    }
}
