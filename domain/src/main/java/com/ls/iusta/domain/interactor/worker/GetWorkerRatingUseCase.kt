package com.ls.iusta.domain.interactor.worker

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.models.worker.GetRatingRequest
import com.ls.iusta.domain.models.worker.Rating
import com.ls.iusta.domain.models.worker.Worker
import com.ls.iusta.domain.models.worker.WorkerRequest
import com.ls.iusta.domain.repository.TicketRepository
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkerRatingUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<GetRatingRequest, Flow<Rating>> {
    override suspend fun invoke(data: GetRatingRequest): Flow<Rating> =
        ticketRepository.getRating(data.ticket_id, data.auth_token)
}