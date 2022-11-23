package com.ls.iusta.domain.models.tickets

import com.ls.iusta.domain.models.UiAwareModel

sealed class TicketDetailUIModel : UiAwareModel() {
    object Loading : TicketDetailUIModel()
    data class Success(val data: Ticket) : TicketDetailUIModel()
    data class Error(var error: String) : TicketDetailUIModel()
    data class BookmarkStatus(val bookmark: Bookmark, val status: Boolean) :
        TicketDetailUIModel()
}

enum class Bookmark {
    BOOKMARK,
    UN_BOOKMARK
}
