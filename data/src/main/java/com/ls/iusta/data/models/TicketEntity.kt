package com.ls.iusta.data.models

data class TicketEntity(
    val archive_flag: String,
    val create_by : String,
    val create_time : String,
    val customer_id : String,
    val customer_user_id : String,
    val dynamic_category_id : String,
    val dynamic_category_name :String,
    val id : String,
    val owner_user_name : String,
    val responsible_user_id :String,
    val service_id : String,
    val ticket_lock_id : String,
    val ticket_priority_id :String,
    val ticket_state_id : String,
    val ticket_state_name : String,
    val title : String,
    val tn : String,
    val type_id : String,
    val user_id : String,
    val category_name : String
)

