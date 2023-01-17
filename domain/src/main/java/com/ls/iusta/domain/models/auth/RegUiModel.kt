package com.ls.iusta.domain.models.auth

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.info.Terms

sealed class RegUiModel : UiAwareModel() {
    object Loading : RegUiModel()
    data class Error(var error: String = "") : RegUiModel()
    data class Success(var result: Boolean) : RegUiModel()
    data class Docs(var data: List<Terms> = emptyList()) : RegUiModel()
    data class SuccessSearch(var data: List<Customer>) : RegUiModel()
}
