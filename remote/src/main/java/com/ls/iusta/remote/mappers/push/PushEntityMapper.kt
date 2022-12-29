package com.ls.iusta.remote.mappers.push

import com.ls.iusta.data.models.push.PushEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.push.PushModel
import javax.inject.Inject

class PushEntityMapper @Inject constructor(
) : EntityMapper<PushModel, PushEntity> {
    override fun mapFromModel(model: PushModel): PushEntity {
        return PushEntity(
            id = model.id,
            worker_id = model.worker_id,
            ticket_id = model.ticket_id,
            title = model.title,
            text = model.text,
            message_id = model.message_id,
            read = model.read,
            read_at = model.read_at,
            created_at = model.created_at
        )
    }
}