package com.ls.iusta.remote.models.ticket

import java.net.URI

data class AttachmentFileModel(
    val name: String?,
    val size: Long?,
    val uri: URI
)

