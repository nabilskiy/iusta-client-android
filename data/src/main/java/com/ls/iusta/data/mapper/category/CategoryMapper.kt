package com.ls.iusta.data.mapper.category

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.CategoryEntity
import com.ls.iusta.data.models.CustomerEntity
import com.ls.iusta.domain.models.category.Category
import com.ls.iusta.domain.models.customer.Customer
import javax.inject.Inject

class CategoryMapper @Inject constructor(
) : Mapper<CategoryEntity, Category> {

    override fun mapFromEntity(type: CategoryEntity): Category {
        return Category(
            id = type.id,
            name = type.name,
            description = type.description,
            icon = type.icon,
            menu = type.menu
        )
    }

    override fun mapToEntity(type: Category): CategoryEntity {
        return CategoryEntity(
            id = type.id,
            name = type.name,
            description = type.description,
            icon = type.icon,
            menu = type.menu
        )
    }
}
