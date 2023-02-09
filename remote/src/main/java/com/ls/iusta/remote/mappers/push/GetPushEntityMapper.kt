package com.ls.iusta.remote.mappers.push

import com.ls.iusta.data.models.push.GetPushEntity
import com.ls.iusta.data.models.ticket.CreateTicketEntity
import com.ls.iusta.data.models.ticket.GetTicketEntity
import com.ls.iusta.data.models.ticket.ShortTicketEntity
import com.ls.iusta.data.models.ticket.TicketEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.push.PushResponseModel
import com.ls.iusta.remote.models.ticket.CreateTicketResponseModel
import com.ls.iusta.remote.models.ticket.ShortTicketModel
import com.ls.iusta.remote.models.ticket.TicketResponseModel
import javax.inject.Inject

class GetPushEntityMapper @Inject constructor(
    private val pushEntityMapper: PushEntityMapper,
) : EntityMapper<PushResponseModel, GetPushEntity> {
    override fun mapFromModel(model: PushResponseModel): GetPushEntity {
        return GetPushEntity(
            success = model.success,
            message = model.message,
            pageNumber = model.pageNumber,
            perPage = model.perPage,
            pageSize = model.pageSize,
            totalPagesCount = model.totalPagesCount,
            response = model.response?.map {
                pushEntityMapper.mapFromModel(it)
            }
        )
    }
}
