package com.ls.iusta.domain.interactor.ticket

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketListUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<GetTicketsRequest, Flow<List<Ticket>>> {
    override suspend fun invoke(data: GetTicketsRequest): Flow<List<Ticket>> =
        ticketRepository.getTickets(data.ticket_status, data.auth_token)
}