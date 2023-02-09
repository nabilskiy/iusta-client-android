package com.ls.iusta.presentation.utils

object AppConstants {
    //const val DB_NAME = "iusta_app.db"

    object Message {
        const val INVALID_TOKEN = "auth_token"
    }

    object Status {
        const val ALL = "all"
        const val OPENED = "opened"
        const val CLOSED = "closed"
    }

    object StatusTickets {
        const val NEW = 1
        const val ACCEPT = 4
        const val ARRIVED = 5
        const val COMPLETE = 7
        const val REJECT = 6
    }
}