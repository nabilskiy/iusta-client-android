package com.ls.iusta.remote.mappers.category

import com.ls.iusta.data.models.CategoryEntity
import com.ls.iusta.data.models.CategoryInfoEntity
import com.ls.iusta.remote.mappers.EntityMapper
import com.ls.iusta.remote.models.category.CategoryModel
import com.ls.iusta.remote.models.category.CategoryResponseInfoModel
import javax.inject.Inject

class CategoryInfoEntityMapper @Inject constructor(
    private val categoryEntityMapper: CategoryEntityMapper
) : EntityMapper<CategoryResponseInfoModel, CategoryInfoEntity> {
    override fun mapFromModel(model: CategoryResponseInfoModel): CategoryInfoEntity {
        return CategoryInfoEntity(
            id = model.id,
            name = model.name,
            back_menu = model.back_menu,
            menus = model.menus.map { categoryEntityMapper.mapFromModel(it) },
            categories = model.categories.map { categoryEntityMapper.mapFromModel(it) }
        )
    }
}
