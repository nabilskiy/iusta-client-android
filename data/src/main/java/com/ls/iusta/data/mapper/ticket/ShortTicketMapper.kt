package com.ls.iusta.data.mapper.ticket

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.ticket.ShortTicketEntity
import com.ls.iusta.domain.models.tickets.ShortTicket
import javax.inject.Inject

class ShortTicketMapper @Inject constructor(
) : Mapper<ShortTicketEntity, ShortTicket> {

    override fun mapFromEntity(type: ShortTicketEntity): ShortTicket {
        return ShortTicket(
            ArticleID = type.ArticleID,
            TicketNumber = type.TicketNumber,
            TicketID = type.TicketID
        )
    }

    override fun mapToEntity(type: ShortTicket): ShortTicketEntity {
        return ShortTicketEntity(
            ArticleID = type.ArticleID,
            TicketNumber = type.TicketNumber,
            TicketID = type.TicketID
        )
    }
}
