package com.ls.iusta.data.mapper.info

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.data.models.info.TermsEntity
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.info.Terms
import javax.inject.Inject

class TermsMapper @Inject constructor(
) : Mapper<TermsEntity, Terms> {

    override fun mapFromEntity(type: TermsEntity): Terms {
        return Terms(
            id = type.id,
            name = type.name,
            lang = type.lang,
            value = type.value,
            url = type.url
        )
    }

    override fun mapToEntity(type: Terms): TermsEntity {
        return TermsEntity(
            id = type.id,
            name = type.name,
            lang = type.lang,
            value = type.value,
            url = type.url
        )
    }
}

