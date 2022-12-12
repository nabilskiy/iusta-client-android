package com.ls.iusta.domain.models.category

data class Category(
    val id: Int,
    val name: String?,
    val description: String?,
    val icon: String?,
    var menu: Boolean = false
)
