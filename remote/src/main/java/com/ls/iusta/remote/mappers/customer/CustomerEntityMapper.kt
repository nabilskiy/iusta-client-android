package com.ls.iusta.remote.mappers.customer

import com.ls.iusta.data.models.CustomerEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.customer.CustomerModel
import javax.inject.Inject

class CustomerEntityMapper @Inject constructor(
) : EntityMapper<CustomerModel, CustomerEntity> {
    override fun mapFromModel(model: CustomerModel): CustomerEntity {
        return CustomerEntity(
            id = model.id,
            name = model.name
        )
    }
}
