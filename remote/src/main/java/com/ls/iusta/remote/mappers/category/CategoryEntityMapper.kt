package com.ls.iusta.remote.mappers.category

import com.ls.iusta.data.models.CategoryEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.category.CategoryModel
import javax.inject.Inject

class CategoryEntityMapper @Inject constructor(
) : EntityMapper<CategoryModel, CategoryEntity> {
    override fun mapFromModel(model: CategoryModel): CategoryEntity {
        return CategoryEntity(
            id = model.id,
            name = model.name,
            description = model.description,
            icon = model.icon,
            menu = model.menu
        )
    }
}
