package com.ls.iusta.domain.models.category

data class CategoryInfo(
    val id: Int?,
    val name: String?,
    val back_menu: Int,
    val menus: List<Category>,
    val categories: List<Category>
)
