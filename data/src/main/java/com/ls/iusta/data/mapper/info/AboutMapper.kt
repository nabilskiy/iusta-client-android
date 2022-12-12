package com.ls.iusta.data.mapper.info

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.info.AboutEntity
import com.ls.iusta.domain.models.info.About
import javax.inject.Inject

class AboutMapper @Inject constructor(
) : Mapper<AboutEntity, About> {

    override fun mapFromEntity(type: AboutEntity): About {
        return About(
            id = type.id,
            name = type.name,
            value = type.value
        )
    }

    override fun mapToEntity(type: About): AboutEntity {
        return AboutEntity(
            id = type.id,
            name = type.name,
            value = type.value
        )
    }
}
