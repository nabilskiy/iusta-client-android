package com.ls.iusta.domain.repository

import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.tickets.ShortTicket
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.models.worker.Rating
import com.ls.iusta.domain.models.worker.Worker
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    // Remote and cache
    suspend fun getTickets(ticket_status: String, auth_token: String?): Flow<List<Ticket>>
    suspend fun getTicket(ticket_status: String, auth_token: String?,  ticketId: Long): Flow<Ticket>


    suspend fun categories(menu_id: String, auth_token: String?): Flow<CategoryInfo>
    suspend fun createTicket(category_id: String, auth_token: String?): Flow<ShortTicket>
    suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): Flow<ShortTicket>

    suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): Flow<String>

    suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): Flow<Boolean>

    suspend fun getRating(
        ticketId: Long,
        auth_token: String?
    ): Flow<Rating>

    suspend fun worker(
        id: Int,
        auth_token: String?
    ): Flow<Worker>




    // Cache
    suspend fun saveTickets(listTickets: List<Ticket>)
    suspend fun getBookMarkedTickets(): Flow<List<Ticket>>
    suspend fun setTicketBookmarked(ticketId: Long): Flow<Int>
    suspend fun setTicketUnBookMarked(ticketId: Long): Flow<Int>
}