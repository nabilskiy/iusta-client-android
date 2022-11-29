package com.ls.iusta.remote.mappers.worker

import com.ls.iusta.data.models.RatingEntity
import com.ls.iusta.data.models.ShortTicketEntity
import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.data.models.WorkerEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.ticket.ShortTicketModel
import com.ls.iusta.remote.models.ticket.TicketModel
import com.ls.iusta.remote.models.worker.RatingModel
import com.ls.iusta.remote.models.worker.WorkerModel
import javax.inject.Inject

class RatingEntityMapper @Inject constructor(
) : EntityMapper<RatingModel, RatingEntity> {
    override fun mapFromModel(model: RatingModel): RatingEntity {
        return RatingEntity(
            rating = model.rating,
            avg_rating = model.avg_rating
        )
    }
}
