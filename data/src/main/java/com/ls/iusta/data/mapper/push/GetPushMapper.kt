package com.ls.iusta.data.mapper.push

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.push.GetPushEntity
import com.ls.iusta.data.models.ticket.CreateTicketEntity
import com.ls.iusta.data.models.ticket.GetTicketEntity
import com.ls.iusta.domain.models.push.GetPush
import com.ls.iusta.domain.models.tickets.CreateTicket
import com.ls.iusta.domain.models.tickets.GetTicket
import com.ls.iusta.domain.models.tickets.Ticket
import javax.inject.Inject

class GetPushMapper @Inject constructor(
    private val pushMapper: PushMapper,
) : Mapper<GetPushEntity, GetPush> {

    override fun mapFromEntity(type: GetPushEntity): GetPush {
        return GetPush(
            success = type.success,
            message = type.message,
            pageNumber = type.pageNumber,
            perPage = type.perPage,
            pageSize = type.pageSize,
            totalPagesCount = type.totalPagesCount,
            response = type.response?.map { pushMapper.mapFromEntity(it) }
        )
    }

    override fun mapToEntity(type: GetPush): GetPushEntity {
        return GetPushEntity(
            success = type.success,
            message = type.message,
            pageNumber = type.pageNumber,
            perPage = type.perPage,
            pageSize = type.pageSize,
            totalPagesCount = type.totalPagesCount,
            response = type.response?.map { pushMapper.mapToEntity(it) }
        )
    }
}
