package com.ls.iusta.domain.models.worker

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.tickets.TicketDetailUIModel

sealed class AddRatingUiModel : UiAwareModel() {
    object Loading : AddRatingUiModel()
    data class Error(var error: String = "") : AddRatingUiModel()
    data class Success(var result: Boolean) : AddRatingUiModel()
    data class WorkerInfo(val data:Worker): AddRatingUiModel()
}
