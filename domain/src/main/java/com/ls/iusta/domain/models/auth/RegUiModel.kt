package com.ls.iusta.domain.models.auth

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.customer.Customer
import com.ls.iusta.domain.models.customer.CustomerResponse
import com.ls.iusta.domain.models.info.Terms
import com.ls.iusta.domain.models.user.Base
import com.ls.iusta.domain.models.user.UserUiModel

sealed class RegUiModel : UiAwareModel() {
    object Loading : RegUiModel()
    data class Error(var error: String = "") : RegUiModel()
    data class Success(var result: Base) : RegUiModel()
    data class Docs(var data: List<Terms> = emptyList()) : RegUiModel()
    data class ChangeLocale(var lang: String) : RegUiModel()
    data class GetLocale(var lang: String) : RegUiModel()
    data class SuccessSearch(var data: CustomerResponse) : RegUiModel()
}
