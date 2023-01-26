package com.ls.iusta.data.mapper.ticket

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.ticket.CreateTicketEntity
import com.ls.iusta.data.models.ticket.GetTicketEntity
import com.ls.iusta.domain.models.tickets.CreateTicket
import com.ls.iusta.domain.models.tickets.GetTicket
import com.ls.iusta.domain.models.tickets.Ticket
import javax.inject.Inject

class GetTicketMapper @Inject constructor(
    private val ticketMapper: TicketMapper,
) : Mapper<GetTicketEntity, GetTicket> {

    override fun mapFromEntity(type: GetTicketEntity): GetTicket {
        return GetTicket(
            success = type.success,
            message = type.message,
            pageNumber = type.pageNumber,
            perPage = type.perPage,
            pageSize = type.pageSize,
            totalPagesCount = type.totalPagesCount,
            response = type.response?.map { ticketMapper.mapFromEntity(it) }
        )
    }

    override fun mapToEntity(type: GetTicket): GetTicketEntity {
        return GetTicketEntity(
            success = type.success,
            message = type.message,
            pageNumber = type.pageNumber,
            perPage = type.perPage,
            pageSize = type.pageSize,
            totalPagesCount = type.totalPagesCount,
            response = type.response?.map { ticketMapper.mapToEntity(it) }
        )
    }
}
