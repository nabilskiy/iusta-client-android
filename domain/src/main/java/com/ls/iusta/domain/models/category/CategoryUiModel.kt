package com.ls.iusta.domain.models.category

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.tickets.AttachmentFile
import com.ls.iusta.domain.models.tickets.CreateTicket
import com.ls.iusta.domain.models.tickets.ShortTicket
import com.ls.iusta.domain.models.user.User

sealed class CategoryUiModel : UiAwareModel() {
    object Loading : CategoryUiModel()
    data class Error(var error: String = "") : CategoryUiModel()
    data class LoadCategoriesSuccess(var data: CategoryInfo) : CategoryUiModel()
    data class CreateTicketSuccess(var data: CreateTicket) : CategoryUiModel()
    data class SizeError(var error: Boolean) : CategoryUiModel()
    data class NoteError(var error: Boolean) : CategoryUiModel()


   // data class Attachments(var list: List<AttachmentFile>) : CategoryUiModel()

}
