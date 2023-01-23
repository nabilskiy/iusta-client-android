package com.ls.iusta.data.mapper.ticket

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.ticket.CreateTicketEntity
import com.ls.iusta.domain.models.tickets.CreateTicket
import javax.inject.Inject

class CreateTicketMapper @Inject constructor(
    private val shortTicketMapper: ShortTicketMapper
) : Mapper<CreateTicketEntity, CreateTicket> {

    override fun mapFromEntity(type: CreateTicketEntity): CreateTicket {
        return CreateTicket(
            success = type.success,
            message = type.message,
            response = type.response?.let { shortTicketMapper.mapFromEntity(it) }
        )
    }

    override fun mapToEntity(type: CreateTicket): CreateTicketEntity {
        return CreateTicketEntity(
            success = type.success,
            message = type.message,
            response = type.response?.let { shortTicketMapper.mapToEntity(it) }
        )
    }
}
