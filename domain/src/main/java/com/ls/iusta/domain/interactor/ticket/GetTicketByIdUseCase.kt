package com.ls.iusta.domain.interactor.ticket

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.tickets.GetTicketByIdRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketByIdUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<GetTicketByIdRequest, Flow<Ticket>> {
    override suspend fun invoke(data: GetTicketByIdRequest): Flow<Ticket> =
        ticketRepository.getTicket(data.ticket_status, data.auth_token, data.ticket_id)
}