package com.ls.iusta.domain.interactor.category

import com.ls.iusta.domain.interactor.BaseUseCase
import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.category.GetCategoryRequest
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryListUseCase @Inject constructor(
    private val ticketRepository: TicketRepository
) : BaseUseCase<GetCategoryRequest, Flow<CategoryInfo>> {
    override suspend fun invoke(data: GetCategoryRequest): Flow<CategoryInfo> =
        ticketRepository.categories(data.menu_id, data.auth_token)
}