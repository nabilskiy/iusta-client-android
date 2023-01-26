package com.ls.iusta.domain.interactor.ticket

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.tickets.GetTicket
import com.ls.iusta.domain.models.tickets.GetTicketByIdRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketByIdUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<GetTicketByIdRequest, Flow<GetTicket>> {
    override suspend fun invoke(data: GetTicketByIdRequest): Flow<GetTicket> =
        ticketRepository.getTicket(data.ticket_id)
}