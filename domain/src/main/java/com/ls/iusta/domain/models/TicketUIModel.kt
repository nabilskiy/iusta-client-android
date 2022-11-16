package com.ls.iusta.domain.models

sealed class TicketUIModel :UiAwareModel(){
    object Loading : TicketUIModel()
    data class Success(val data: List<Ticket>) : TicketUIModel()
    data class Error(var error: String) : TicketUIModel()
}
