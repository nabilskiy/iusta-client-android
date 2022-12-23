package com.ls.iusta.domain.interactor.worker

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.models.worker.*
import com.ls.iusta.domain.repository.TicketRepository
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetWorkerRatingUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<SetRatingRequest, Flow<Boolean>> {
    override suspend fun invoke(data: SetRatingRequest): Flow<Boolean> =
        ticketRepository.addRating(
            data.worker_rating,
            data.ticket_id,
            data.ticket_note
        )
}