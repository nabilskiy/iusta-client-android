package com.ls.iusta.domain.interactor.push

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePushesUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<String, Flow<Boolean>> {
    override suspend fun invoke(ids: String): Flow<Boolean> =
        userRepository.deletePush(ids)
}