package com.ls.iusta.domain.models.push

import com.ls.iusta.domain.models.UiAwareModel

sealed class NotificationsUIModel : UiAwareModel(){
    object Loading : NotificationsUIModel()
    data class Success(val data: GetPush) : NotificationsUIModel()
    data class Error(var error: String) : NotificationsUIModel()
    data class Read(var result: Boolean) : NotificationsUIModel()
    data class Delete(var result: Boolean) : NotificationsUIModel()
}
