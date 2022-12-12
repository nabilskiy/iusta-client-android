package com.ls.iusta.remote.mappers.info

import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.data.models.info.FaqEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.customer.CustomerModel
import com.ls.iusta.remote.models.faq.FaqModel
import javax.inject.Inject

class FaqEntityMapper @Inject constructor(
) : EntityMapper<FaqModel, FaqEntity> {
    override fun mapFromModel(model: FaqModel): FaqEntity {
        return FaqEntity(
            id = model.id,
            lang = model.lang,
            question = model.question,
            answer = model.answer
        )
    }
}
