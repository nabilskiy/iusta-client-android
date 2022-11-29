package com.ls.iusta.remote.mappers.worker

import com.ls.iusta.data.models.ShortTicketEntity
import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.models.WorkerEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.ticket.ShortTicketModel
import com.ls.iusta.remote.models.ticket.TicketModel
import com.ls.iusta.remote.models.worker.WorkerModel
import javax.inject.Inject

class WorkerEntityMapper @Inject constructor(
) : EntityMapper<WorkerModel, WorkerEntity> {
    override fun mapFromModel(model: WorkerModel): WorkerEntity {
        return WorkerEntity(
            firstname = model.firstname,
            lastname = model.lastname,
            phone_number = model.phone_number,
            image = model.image,
            avg_rating = model.avg_rating
        )
    }
}
