package com.ls.iusta.domain.models.category

data class GetCategoryRequest(
    val menu_id: Int,
    var auth_token: String?
)
