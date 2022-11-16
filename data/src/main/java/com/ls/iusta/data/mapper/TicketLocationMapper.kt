package com.ls.iusta.data.mapper

import com.ls.iusta.data.models.TicketLocationEntity
import com.ls.iusta.domain.models.TicketLocation
import javax.inject.Inject

class TicketLocationMapper @Inject constructor() :
    Mapper<TicketLocationEntity, TicketLocation> {

    override fun mapFromEntity(type: TicketLocationEntity): TicketLocation {
        return TicketLocation(name = type.name, url = type.url)
    }

    override fun mapToEntity(type: TicketLocation): TicketLocationEntity {
        return TicketLocationEntity(name = type.name, url = type.url)
    }
}
