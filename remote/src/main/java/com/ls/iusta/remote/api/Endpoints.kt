package com.ls.iusta.remote.api

class Endpoints {

    companion object {
        const val USER_LOGIN = "mobile/login"
        const val USER_RESET_PASSWORD = "mobile/password_reset"
        const val USER_UPDATE_PASSWORD = "mobile/password_update"
        const val USER_LOGOUT = "mobile/logout"

        const val USER_INFO = "mobile/contact_info"
        const val USER_INFO_ADD = "mobile/contact_add"
        const val USER_INFO_EDIT = "mobile/contact_edit"

        const val CUSTOMERS = "mobile/request_il_customer_list"
        const val CATEGORIES = "mobile/request_categories_list"

        const val TICKET_CREATE = "mobile/tickets_create"
        const val TICKET_NOTE = "mobile/tickets_note"
        const val TICKET_HISTORY = "mobile/tickets_history"
        const val TICKET_ACTIVE = "mobile/tickets_active"
        const val TICKET_NOTE_INFO = "mobile/tickets_note_info"

        const val WORKER_RATING = "mobile/worker_rating"
        const val WORKER_RATING_INFO = "mobile/worker_rating_info"
        const val WORKER_INFO = "mobile/worker_info"

        const val PUSH_TOKEN_SAVE = "mobile/push_token_save"
        const val PUSH_MESSAGES = "mobile/push_notification_messages"
        const val PUSH_READ = "mobile/push_notification_update"
        const val PUSH_DELETE = "mobile/push_notification_delete"

        const val INFO_ABOUT = "mobile/about"
        const val INFO_FAQ = "mobile/faq"
        const val INFO_DOCS = "mobile/docs"


    }
}