package com.ls.iusta.data.mapper

import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.domain.models.Ticket
import javax.inject.Inject

class TicketMapper @Inject constructor(
    private val locationMapper: TicketLocationMapper
) : Mapper<TicketEntity, Ticket> {

    override fun mapFromEntity(type: TicketEntity): Ticket {
        return Ticket(
            created = type.created,
            gender = type.gender,
            id = type.id,
            image = type.image,
            characterLocation = locationMapper.mapFromEntity(type.characterLocation),
            name = type.name,
            species = type.species,
            status = type.status,
            type = type.type,
            url = type.url,
            isBookMarked = type.isBookMarked
        )
    }

    override fun mapToEntity(type: Ticket): TicketEntity {
        return TicketEntity(
            created = type.created,
            gender = type.gender,
            id = type.id,
            image = type.image,
            characterLocation = locationMapper.mapToEntity(type.characterLocation),
            name = type.name,
            species = type.species,
            status = type.status,
            type = type.type,
            url = type.url,
            isBookMarked = type.isBookMarked
        )
    }
}
