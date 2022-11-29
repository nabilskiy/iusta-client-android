package com.ls.iusta.data.models

data class CategoryInfoEntity(
    val id: Int?,
    val name: String?,
    val back_menu: Int?,
    val menus: List<CategoryEntity>,
    val categories: List<CategoryEntity>
)
