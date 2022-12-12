package com.ls.iusta.data.mapper.info

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.data.models.info.AboutEntity
import com.ls.iusta.data.models.info.FaqEntity
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.info.About
import com.ls.iusta.domain.models.info.Faq
import javax.inject.Inject

class FaqMapper @Inject constructor(
) : Mapper<FaqEntity, Faq> {

    override fun mapFromEntity(type: FaqEntity): Faq {
        return Faq(
            id = type.id,
            lang = type.lang,
            question = type.question,
            answer = type.answer
        )
    }

    override fun mapToEntity(type: Faq): FaqEntity {
        return FaqEntity(
            id = type.id,
            lang = type.lang,
            question = type.question,
            answer = type.answer
        )
    }
}

