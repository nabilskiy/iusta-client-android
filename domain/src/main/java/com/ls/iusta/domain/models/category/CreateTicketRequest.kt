package com.ls.iusta.domain.models.category

import com.ls.iusta.domain.models.tickets.AttachmentFile

data class CreateTicketRequest(
    val attachments: List<AttachmentFile>,
    val category_id: Int,
    var note: String?
)
