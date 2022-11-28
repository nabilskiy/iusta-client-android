package com.ls.iusta.remote.models.category

import com.ls.iusta.remote.models.customer.CustomerModel


data class CategoryResponseInfoModel (
    val id: Int?,
    val name: String?,
    val back_menu: Int?,
    val menus: List<CategoryModel>?,
    val categories: List<CategoryModel>?
)
