package com.ls.iusta.cache.mapper

import com.ls.iusta.cache.models.TicketCacheEntity
import com.ls.iusta.data.models.TicketEntity
import javax.inject.Inject

class TicketCacheMapper @Inject constructor(
    private val ticketLocationCacheMapper: TicketLocationCacheMapper
) : CacheMapper<TicketCacheEntity, TicketEntity> {

    override fun mapFromCached(type: TicketCacheEntity): TicketEntity {
        return TicketEntity(
            created = type.created,
            gender = type.gender,
            id = type.id,
            image = type.image,
            characterLocation = ticketLocationCacheMapper.mapFromCached(type.characterLocation),
            name = type.name,
            species = type.species,
            status = type.status,
            type = type.type,
            url = type.url,
            isBookMarked = type.isBookMarked
        )
    }

    override fun mapToCached(type: TicketEntity): TicketCacheEntity {
        return TicketCacheEntity(
            created = type.created,
            gender = type.gender,
            id = type.id,
            image = type.image,
            characterLocation = ticketLocationCacheMapper.mapToCached(type.characterLocation),
            name = type.name,
            species = type.species,
            status = type.status,
            type = type.type,
            url = type.url,
            isBookMarked = type.isBookMarked
        )
    }
}
