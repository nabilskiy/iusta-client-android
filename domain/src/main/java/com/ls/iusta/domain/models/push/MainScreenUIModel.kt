package com.ls.iusta.domain.models.push

import com.ls.iusta.domain.models.UiAwareModel

sealed class MainScreenUIModel : UiAwareModel(){
    object Loading : MainScreenUIModel()
    data class Success(val data: List<Push>) : MainScreenUIModel()
    data class Error(var error: String) : MainScreenUIModel()
    data class Token(var result: Boolean) : MainScreenUIModel()
    data class User(var data: com.ls.iusta.domain.models.user.User) : MainScreenUIModel()
}
