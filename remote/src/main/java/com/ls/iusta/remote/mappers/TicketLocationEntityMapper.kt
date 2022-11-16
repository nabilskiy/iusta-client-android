package com.ls.iusta.remote.mappers

import com.ls.iusta.data.models.TicketLocationEntity
import com.ls.iusta.remote.models.TicketLocationModel
import javax.inject.Inject

class TicketLocationEntityMapper @Inject constructor() :
    EntityMapper<TicketLocationModel, TicketLocationEntity> {
    override fun mapFromModel(model: TicketLocationModel): TicketLocationEntity {
        return TicketLocationEntity(name = model.name, url = model.url)
    }
}
