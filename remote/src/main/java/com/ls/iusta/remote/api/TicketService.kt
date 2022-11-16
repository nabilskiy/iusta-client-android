package com.ls.iusta.remote.api

import com.ls.iusta.remote.models.TicketResponseModel
import com.ls.iusta.remote.models.TicketModel
import retrofit2.http.GET
import retrofit2.http.Path

interface TicketService {

    @GET("character")
    suspend fun getTickets(): TicketResponseModel

    @GET("character/{id}")
    suspend fun getTicket(@Path("id") id: Long): TicketModel
}
