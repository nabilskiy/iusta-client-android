package com.ls.iusta.remote.mappers.ticket

import com.ls.iusta.data.models.ticket.ShortTicketEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.ticket.ShortTicketModel
import javax.inject.Inject

class ShortTicketEntityMapper @Inject constructor(
) : EntityMapper<ShortTicketModel, ShortTicketEntity> {
    override fun mapFromModel(model: ShortTicketModel): ShortTicketEntity {
        return ShortTicketEntity(
            ArticleID = model.ArticleID,
            TicketNumber = model.TicketNumber,
            TicketID = model.TicketID
        )
    }
}
