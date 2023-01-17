package com.ls.iusta.data.mapper.user

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.BaseModelEntity
import com.ls.iusta.data.models.user.LoginEntity
import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.user.Base
import javax.inject.Inject

class BaseMapper @Inject constructor(
) : Mapper<BaseModelEntity, Base> {

    override fun mapFromEntity(type: BaseModelEntity): Base {
        return Base(
            success = type.success,
            message = type.message
        )
    }

    override fun mapToEntity(type: Base): BaseModelEntity {
        return BaseModelEntity(
            success = type.success,
            message = type.message
        )
    }
}
