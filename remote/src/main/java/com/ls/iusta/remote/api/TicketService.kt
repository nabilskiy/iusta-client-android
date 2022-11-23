package com.ls.iusta.remote.api

import com.ls.iusta.remote.models.TicketResponseModel
import com.ls.iusta.remote.models.LoginModel
import com.ls.iusta.remote.models.TicketModel
import retrofit2.http.*

interface TicketService {

    @FormUrlEncoded
    @POST("mobile/tickets_history")
    suspend fun getTickets(@Field("ticket_status") ticket_status: String,
                           @Field("auth_token") auth_token: String,
                           @Field("secret_key") secret_key: String): TicketResponseModel

    @FormUrlEncoded
    @POST("mobile/tickets_history")
    suspend fun getTicket(@Field("ticket_status") status: String,
                          @Field("auth_token") auth_token: String,
                          @Field("secret_key") secret_key: String,
                          @Field("ticket_id") id: Long): TicketResponseModel

}
