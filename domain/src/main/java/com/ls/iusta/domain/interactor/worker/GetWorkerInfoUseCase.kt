package com.ls.iusta.domain.interactor.worker

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.models.worker.Worker
import com.ls.iusta.domain.models.worker.WorkerRequest
import com.ls.iusta.domain.repository.TicketRepository
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkerInfoUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<WorkerRequest, Flow<Worker>> {
    override suspend fun invoke(data: WorkerRequest): Flow<Worker> =
        ticketRepository.worker(data.id, data.auth_token)
}