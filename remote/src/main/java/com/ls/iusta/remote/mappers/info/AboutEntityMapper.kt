package com.ls.iusta.remote.mappers.info

import com.ls.iusta.data.models.customer.CustomerEntity
import com.ls.iusta.data.models.info.AboutEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.about.AboutModel
import com.ls.iusta.remote.models.customer.CustomerModel
import javax.inject.Inject

class AboutEntityMapper @Inject constructor(
) : EntityMapper<AboutModel, AboutEntity> {
    override fun mapFromModel(model: AboutModel): AboutEntity {
        return AboutEntity(
            id = model.id,
            name = model.name,
            value = model.value
        )
    }
}
