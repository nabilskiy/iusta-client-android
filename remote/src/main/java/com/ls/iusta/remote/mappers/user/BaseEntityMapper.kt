package com.ls.iusta.remote.mappers.user

import com.ls.iusta.data.models.BaseModelEntity
import com.ls.iusta.data.models.user.LoginEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.BaseModel
import com.ls.iusta.remote.models.user.LoginModel
import javax.inject.Inject

class BaseEntityMapper @Inject constructor(
) : EntityMapper<BaseModel, BaseModelEntity> {
    override fun mapFromModel(model: BaseModel): BaseModelEntity {
        return BaseModelEntity(
            success = model.success,
            message = model.message
        )
    }
}
