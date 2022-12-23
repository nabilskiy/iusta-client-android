package com.ls.iusta.domain.models.tickets


import java.io.File
import java.net.URI


data class AttachmentFile(
    val name: String?,
    val size: Long?,
    val uri: URI,
    val file: File
)

