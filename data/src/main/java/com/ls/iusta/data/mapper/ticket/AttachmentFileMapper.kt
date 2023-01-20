package com.ls.iusta.data.mapper.ticket

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.ticket.AttachmentFileEntity
import com.ls.iusta.domain.models.tickets.AttachmentFile
import javax.inject.Inject

class AttachmentFileMapper @Inject constructor(
) : Mapper<AttachmentFileEntity, AttachmentFile> {

    override fun mapFromEntity(type: AttachmentFileEntity): AttachmentFile {
        return AttachmentFile(
            name = type.name,
            size = type.size,
            file = type.file
        )
    }

    override fun mapToEntity(type: AttachmentFile): AttachmentFileEntity {
        return AttachmentFileEntity(
            name = type.name,
            size = type.size,
            file = type.file
        )
    }
}
