package com.ls.iusta.data.mapper.worker

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.CustomerEntity
import com.ls.iusta.data.models.WorkerEntity
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.worker.Worker
import javax.inject.Inject

class WorkerMapper @Inject constructor(
) : Mapper<WorkerEntity, Worker> {

    override fun mapFromEntity(type: WorkerEntity): Worker {
        return Worker(
            firstname = type.firstname,
            lastname = type.lastname,
            phone_number = type.phone_number,
            image = type.image,
            avg_rating = type.avg_rating
        )
    }

    override fun mapToEntity(type: Worker): WorkerEntity {
        return WorkerEntity(
            firstname = type.firstname,
            lastname = type.lastname,
            phone_number = type.phone_number,
            image = type.image,
            avg_rating = type.avg_rating
        )
    }
}
