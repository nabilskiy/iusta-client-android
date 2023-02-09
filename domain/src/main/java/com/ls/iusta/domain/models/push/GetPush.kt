package com.ls.iusta.domain.models.push

data class GetPush(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    var pageNumber: Int?,
    var perPage: Int,
    var pageSize: Int,
    var totalPagesCount: Int?,
    val response: List<Push>?
)
