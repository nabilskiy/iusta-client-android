package com.ls.iusta.data.mapper.customer

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.domain.models.customer.Customer
import javax.inject.Inject

class CustomerMapper @Inject constructor(
) : Mapper<CustomerEntity, Customer> {

    override fun mapFromEntity(type: CustomerEntity): Customer {
        return Customer(
            id = type.id,
            name = type.name
        )
    }

    override fun mapToEntity(type: Customer): CustomerEntity {
        return CustomerEntity(
            id = type.id,
            name = type.name
        )
    }
}
