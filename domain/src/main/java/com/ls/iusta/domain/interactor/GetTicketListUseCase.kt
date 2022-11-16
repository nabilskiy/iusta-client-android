package com.ls.iusta.domain.interactor

import com.ls.iusta.domain.models.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketListUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<Unit, Flow<List<Ticket>>> {
    override suspend fun invoke(params: Unit): Flow<List<Ticket>> =
        ticketRepository.getTickets()
}