package com.ls.iusta.data.mapper.worker

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.CustomerEntity
import com.ls.iusta.data.models.RatingEntity
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.worker.Rating
import javax.inject.Inject

class RatingMapper @Inject constructor(
) : Mapper<RatingEntity, Rating> {

    override fun mapFromEntity(type: RatingEntity): Rating {
        return Rating(
            rating = type.rating,
            avg_rating = type.avg_rating
        )
    }

    override fun mapToEntity(type: Rating): RatingEntity {
        return RatingEntity(
            rating = type.rating,
            avg_rating = type.avg_rating
        )
    }
}
