package com.ls.iusta.domain.interactor.info

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.info.About
import com.ls.iusta.domain.models.info.GetInfoRequest
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAboutUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<Unit, Flow<List<About>>> {
    override suspend fun invoke(params: Unit): Flow<List<About>> =
        userRepository.about()
}