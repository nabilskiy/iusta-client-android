package com.ls.iusta.remote.models


data class BaseModel (
    var success: Boolean,
    val message: String?,
    val error: String?
)
