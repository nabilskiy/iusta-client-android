package com.ls.iusta.data.mapper.push

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.push.PushEntity
import com.ls.iusta.data.models.worker.RatingEntity
import com.ls.iusta.domain.models.push.Push
import com.ls.iusta.domain.models.worker.Rating
import javax.inject.Inject

class PushMapper @Inject constructor(
) : Mapper<PushEntity, Push> {

    override fun mapFromEntity(model: PushEntity): Push {
        return Push(
            id = model.id,
            contact_id = model.contact_id,
            ticket_id = model.ticket_id,
            title = model.title,
            text = model.text,
            message_id = model.message_id,
            read = model.read,
            read_at = model.read_at,
            created_at = model.created_at
        )
    }

    override fun mapToEntity(model: Push): PushEntity {
        return PushEntity(
            id = model.id,
            contact_id = model.contact_id,
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
