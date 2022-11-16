package com.ls.iusta.domain.interactor

import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetTicketBookmarkUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<Long, Flow<Int>> {

    override suspend operator fun invoke(params: Long): Flow<Int> =
        ticketRepository.setTicketBookmarked(params)
}
