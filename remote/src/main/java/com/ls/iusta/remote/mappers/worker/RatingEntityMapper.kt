package com.ls.iusta.remote.mappers.worker

import com.ls.iusta.data.models.worker.RatingEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.worker.RatingModel
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
