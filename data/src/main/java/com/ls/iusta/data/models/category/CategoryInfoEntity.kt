package com.ls.iusta.data.models.category

data class CategoryInfoEntity(
    val id: Int?,
    val name: String?,
    val back_menu: Int?,
    val menus: List<CategoryEntity>,
    val categories: List<CategoryEntity>
)
