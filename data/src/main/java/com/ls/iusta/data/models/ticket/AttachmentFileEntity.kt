package com.ls.iusta.data.models.ticket

import java.io.File
import java.net.URI

data class AttachmentFileEntity(
    val name: String?,
    val size: Long?,
    val uri: URI,
    val file: File
)

