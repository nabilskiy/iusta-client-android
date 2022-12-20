package com.ls.iusta.remote.mappers.info

import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.data.models.info.TermsEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.customer.CustomerModel
import com.ls.iusta.remote.models.terms.TermsModel
import javax.inject.Inject

class TermsEntityMapper @Inject constructor(
) : EntityMapper<TermsModel, TermsEntity> {
    override fun mapFromModel(model: TermsModel): TermsEntity {
        return TermsEntity(
            id = model.id,
            lang = model.lang,
            name = model.name,
            value = model.value
        )
    }
}