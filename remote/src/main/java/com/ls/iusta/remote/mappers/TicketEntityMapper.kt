package com.ls.iusta.remote.mappers

import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.remote.models.TicketModel
import javax.inject.Inject

class TicketEntityMapper @Inject constructor(
    private val ticketLocationEntityMapper: TicketLocationEntityMapper
) : EntityMapper<TicketModel, TicketEntity> {
    override fun mapFromModel(model: TicketModel): TicketEntity {
        return TicketEntity(
            created = model.created,
            gender = model.gender,
            id = model.id,
            image = model.image,
            characterLocation = ticketLocationEntityMapper.mapFromModel(model.characterLocation),
            name = model.name,
            species = model.species,
            status = model.status,
            type = model.type,
            url = model.url,
            isBookMarked = model.isBookMarked
        )
    }
}
