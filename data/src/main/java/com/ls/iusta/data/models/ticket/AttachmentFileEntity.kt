package com.ls.iusta.data.models.ticket

import java.io.File

data class AttachmentFileEntity(
    val name: String?,
    val size: Long?,
    val file: File
)

