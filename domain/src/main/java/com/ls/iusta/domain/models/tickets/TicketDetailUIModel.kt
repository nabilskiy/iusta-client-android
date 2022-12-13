package com.ls.iusta.domain.models.tickets

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.worker.Worker

sealed class TicketDetailUIModel : UiAwareModel() {
    object Loading : TicketDetailUIModel()
    data class Success(val data: Ticket) : TicketDetailUIModel()
    data class Error(var error: String) : TicketDetailUIModel()
    data class WorkerInfo(val data:Worker): TicketDetailUIModel()
    data class AddNote(val data:ShortTicket): TicketDetailUIModel()
}

