package com.ls.iusta.remote.mappers.ticket

import com.ls.iusta.data.models.ticket.AttachmentFileEntity
import com.ls.iusta.remote.mappers.EntityMapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

class AttachmentFileEntityMapper @Inject constructor(

) : EntityMapper<AttachmentFileEntity, MultipartBody.Part> {
    override fun mapFromModel(model: AttachmentFileEntity): MultipartBody.Part {
        return MultipartBody.Part.createFormData(
            "attachments[]",
            model.name,
            model.file.asRequestBody("image/*".toMediaTypeOrNull())
        )
    }
}