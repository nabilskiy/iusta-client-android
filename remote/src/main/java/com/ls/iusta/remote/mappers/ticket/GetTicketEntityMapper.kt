package com.ls.iusta.remote.mappers.ticket

import com.ls.iusta.data.models.ticket.CreateTicketEntity
import com.ls.iusta.data.models.ticket.GetTicketEntity
import com.ls.iusta.data.models.ticket.ShortTicketEntity
import com.ls.iusta.data.models.ticket.TicketEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.ticket.CreateTicketResponseModel
import com.ls.iusta.remote.models.ticket.ShortTicketModel
import com.ls.iusta.remote.models.ticket.TicketResponseModel
import javax.inject.Inject

class GetTicketEntityMapper @Inject constructor(
    private val ticketEntityMapper: TicketEntityMapper,
) : EntityMapper<TicketResponseModel, GetTicketEntity> {
    override fun mapFromModel(model: TicketResponseModel): GetTicketEntity {
        return GetTicketEntity(
            success = model.success,
            message = model.message,
            pageNumber = model.pageNumber,
            perPage = model.perPage,
            pageSize = model.pageSize,
            totalPagesCount = model.totalPagesCount,
            response = model.response?.map {
                ticketEntityMapper.mapFromModel(it)
            }
        )
    }
}
