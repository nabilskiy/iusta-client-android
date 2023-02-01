package com.ls.iusta.remote.mappers.customer

import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.data.models.customer.CustomerResponseEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.customer.CustomerModel
import com.ls.iusta.remote.models.customer.CustomerResponseModel
import javax.inject.Inject

class CustomerResponseEntityMapper @Inject constructor(
    private val customerEntityMapper: CustomerEntityMapper
) : EntityMapper<CustomerResponseModel, CustomerResponseEntity> {
    override fun mapFromModel(model: CustomerResponseModel): CustomerResponseEntity {
        return CustomerResponseEntity(
            success = model.success,
            message = model.message,
            response = model.response?.map {
                customerEntityMapper.mapFromModel(it)
            }
        )
    }
}
