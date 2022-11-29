package com.ls.iusta.domain.interactor.customer

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCustomersListUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<String, Flow<List<Customer>>> {
    override suspend fun invoke(query: String): Flow<List<Customer>> =
        userRepository.customers(query)
}