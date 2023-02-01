package com.ls.iusta.data.mapper.customer

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.mapper.ticket.TicketMapper
import com.ls.iusta.data.models.customer.CustomerResponseEntity
import com.ls.iusta.data.models.ticket.CreateTicketEntity
import com.ls.iusta.data.models.ticket.GetTicketEntity
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.customer.CustomerResponse
import com.ls.iusta.domain.models.tickets.CreateTicket
import com.ls.iusta.domain.models.tickets.GetTicket
import com.ls.iusta.domain.models.tickets.Ticket
import javax.inject.Inject

class CustomerResponseMapper @Inject constructor(
    private val customerMapper: CustomerMapper,
) : Mapper<CustomerResponseEntity, CustomerResponse> {

    override fun mapFromEntity(type: CustomerResponseEntity): CustomerResponse {
        return CustomerResponse(
            success = type.success,
            message = type.message,
            response = type.response?.map { customerMapper.mapFromEntity(it) }
        )
    }

    override fun mapToEntity(type: CustomerResponse): CustomerResponseEntity {
        return CustomerResponseEntity(
            success = type.success,
            message = type.message,
            response = type.response?.map { customerMapper.mapToEntity(it) }
        )
    }
}
