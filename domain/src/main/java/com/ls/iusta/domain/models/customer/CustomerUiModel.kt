package com.ls.iusta.domain.models.customer

import com.ls.iusta.domain.models.UiAwareModel
import com.ls.iusta.domain.models.auth.Login

sealed class CustomerUiModel : UiAwareModel() {
    object Loading : CustomerUiModel()
    data class Error(var error: String = "") : CustomerUiModel()
    data class Success(var data: List<Customer>) : CustomerUiModel()
}
