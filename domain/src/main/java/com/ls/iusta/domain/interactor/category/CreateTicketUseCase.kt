package com.ls.iusta.domain.interactor.category

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.category.CreateTicketRequest
import com.ls.iusta.domain.models.category.GetCategoryRequest
import com.ls.iusta.domain.models.tickets.ShortTicket
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateTicketUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<CreateTicketRequest, Flow<ShortTicket>> {
    override suspend fun invoke(data: CreateTicketRequest): Flow<ShortTicket> =
        ticketRepository.createTicket(data.category_id, data.auth_token)
}