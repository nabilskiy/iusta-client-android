package com.ls.iusta.remote.models


data class BaseModel (
    var success: Boolean,
    val message: Map<String, List<String>>?,
    val error: String?
)
