package com.ls.iusta.domain.interactor.push

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.push.GetPush
import com.ls.iusta.domain.models.push.Push
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPushListUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<Int, Flow<GetPush>> {
    override suspend fun invoke(page: Int): Flow<GetPush> =
        userRepository.notifications(page)
}