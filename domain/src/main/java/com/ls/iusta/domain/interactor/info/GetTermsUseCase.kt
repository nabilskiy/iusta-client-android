package com.ls.iusta.domain.interactor.info

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.info.About
import com.ls.iusta.domain.models.info.GetInfoRequest
import com.ls.iusta.domain.models.info.Terms
import com.ls.iusta.domain.models.tickets.GetTicketsRequest
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import com.ls.iusta.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTermsUseCase @Inject constructor(
    private val userRepository: UserRepository
) : BaseUseCase<GetInfoRequest, Flow<List<Terms>>> {
    override suspend fun invoke(data: GetInfoRequest): Flow<List<Terms>> =
        userRepository.terms(data.lang, data.auth_token)
}