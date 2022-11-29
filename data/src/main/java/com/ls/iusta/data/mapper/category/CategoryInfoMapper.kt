package com.ls.iusta.data.mapper.category

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.CategoryInfoEntity
import com.ls.iusta.data.models.CustomerEntity
import com.ls.iusta.domain.models.category.Category
import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.customer.Customer
import javax.inject.Inject

class CategoryInfoMapper @Inject constructor(
    private val categoryMapper: CategoryMapper
) : Mapper<CategoryInfoEntity, CategoryInfo> {

    override fun mapFromEntity(type: CategoryInfoEntity): CategoryInfo {
        return CategoryInfo(
            id = type.id,
            name = type.name,
            back_menu = type.back_menu,
            menus = type.menus.map { categoryMapper.mapFromEntity(it) },
            categories = type.categories.map {  categoryMapper.mapFromEntity(it) }
        )
    }

    override fun mapToEntity(type: CategoryInfo): CategoryInfoEntity {
        return CategoryInfoEntity(
            id = type.id,
            name = type.name,
            back_menu = type.back_menu,
            menus = type.menus.map { categoryMapper.mapToEntity(it) },
            categories = type.categories.map {  categoryMapper.mapToEntity(it) }
        )
    }
}
