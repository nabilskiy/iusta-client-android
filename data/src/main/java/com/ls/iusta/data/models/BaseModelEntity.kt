package com.ls.iusta.data.models

data class BaseModelEntity(
    var success: Boolean?,
    val message: Map<String, List<String>>?
)
