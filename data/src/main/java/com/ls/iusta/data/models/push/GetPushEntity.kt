package com.ls.iusta.data.models.push

data class GetPushEntity(
    var success: Boolean,
    val message: Map<String, List<String>>?,
    var pageNumber: Int?,
    var perPage: Int,
    var pageSize: Int,
    var totalPagesCount: Int?,
    val response: List<PushEntity>?
)
