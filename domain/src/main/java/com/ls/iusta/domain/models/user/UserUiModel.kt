package com.ls.iusta.domain.models.user

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.auth.Login
import com.ls.iusta.domain.models.auth.RegUiModel
import com.ls.iusta.domain.models.customer.CustomerResponse

sealed class UserUiModel : UiAwareModel() {
    object Loading : UserUiModel()
    data class Error(var error: String = "") : UserUiModel()
    data class Success(var data: User) : UserUiModel()
    data class Updated(var result: Base) : UserUiModel()
    data class UpdatedLocale(var lang: String) : UserUiModel()
    data class ChangeLocale(var lang: String) : UserUiModel()
    data class GetLocale(var lang: String) : UserUiModel()
    data class SuccessSearch(var data: CustomerResponse) : UserUiModel()

   // data class SuccessUpdate() : UserUiModel()
}
