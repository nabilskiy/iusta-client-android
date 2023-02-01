package com.ls.iusta.domain.models.customer

import com.ls.iusta.domain.models.tickets.Ticket

data class CustomerResponse(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    val response: List<Customer>?
)
