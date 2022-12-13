package com.ls.iusta.domain.interactor.ticket

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.category.CreateTicketRequest
import com.ls.iusta.domain.models.category.GetCategoryRequest
import com.ls.iusta.domain.models.tickets.CreateNoteRequest
import com.ls.iusta.domain.models.tickets.ShortTicket
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<CreateNoteRequest, Flow<ShortTicket>> {
    override suspend fun invoke(data: CreateNoteRequest): Flow<ShortTicket> =
        ticketRepository.addNoteTicket(data.ticketId, data.ticketNote, data.auth_token)
}

