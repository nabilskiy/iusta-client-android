package com.ls.iusta.data.models.customer

data class CustomerResponseEntity(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    val response: List<CustomerEntity>?
)
