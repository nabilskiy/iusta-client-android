package com.ls.iusta.domain.interactor

import com.ls.iusta.domain.models.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketByIdUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<Long, Flow<Ticket>> {
    override suspend fun invoke(params: Long): Flow<Ticket> =
        ticketRepository.getTicket(params)
}