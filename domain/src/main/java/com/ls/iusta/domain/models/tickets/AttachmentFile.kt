package com.ls.iusta.domain.models.tickets

import java.io.File

data class AttachmentFile(
    val name: String?,
    val size: Long?,
    val file: File
)

