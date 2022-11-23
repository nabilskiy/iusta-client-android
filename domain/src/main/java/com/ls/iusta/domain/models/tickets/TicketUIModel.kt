package com.ls.iusta.domain.models.tickets

import com.ls.iusta.domain.models.UiAwareModel

sealed class TicketUIModel : UiAwareModel(){
    object Loading : TicketUIModel()
    data class Success(val data: List<Ticket>) : TicketUIModel()
    data class Error(var error: String) : TicketUIModel()
}
