package com.ls.iusta.remote.mappers.ticket

import com.ls.iusta.data.models.ticket.CreateTicketEntity
import com.ls.iusta.data.models.ticket.ShortTicketEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.ticket.CreateTicketResponseModel
import com.ls.iusta.remote.models.ticket.ShortTicketModel
import javax.inject.Inject

class CreateTicketEntityMapper @Inject constructor(
    private val shortTicketEntityMapper: ShortTicketEntityMapper
) : EntityMapper<CreateTicketResponseModel, CreateTicketEntity> {
    override fun mapFromModel(model: CreateTicketResponseModel): CreateTicketEntity {
        return CreateTicketEntity(
            success = model.success,
            message = model.message,
            response = model.response?.let { shortTicketEntityMapper.mapFromModel(it) }
        )
    }
}
