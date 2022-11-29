package com.ls.iusta.domain.interactor.ticket

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetTicketUnBookmarkUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<Long, Flow<Int>> {
    override suspend operator fun invoke(params: Long) =
        ticketRepository.setTicketUnBookMarked(params)
}