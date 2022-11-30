package com.ls.iusta.data.source

import com.ls.iusta.data.models.*
import com.ls.iusta.data.repository.TicketCache
import com.ls.iusta.data.repository.TicketDataSource
import javax.inject.Inject

class TicketCacheDataSource @Inject constructor(
    private val ticketCache: TicketCache
) : TicketDataSource {
    override suspend fun getTickets(ticket_status: String, auth_token: String?): List<TicketEntity> =
        ticketCache.getTickets()

    override suspend fun getTicket(ticket_status: String, auth_token: String?, ticketId: Long): TicketEntity =
        ticketCache.getTicket(ticketId)


    override suspend fun categories(menu_id: String, auth_token: String?): CategoryInfoEntity {
        TODO("Not yet implemented")
    }

    override suspend fun createTicket(category_id: String, auth_token: String?): ShortTicketEntity {
        TODO("Not yet implemented")
    }

    override suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): ShortTicketEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): String {
        TODO("Not yet implemented")
    }

    override suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getRating(ticketId: Long, auth_token: String?): RatingEntity {
        TODO("Not yet implemented")
    }

    override suspend fun worker(id: Int, auth_token: String?): WorkerEntity {
        TODO("Not yet implemented")
    }

    override suspend fun saveTicket(listTickets: List<TicketEntity>) =
        ticketCache.saveTickets(listTickets)

    override suspend fun getBookMarkedTickets(): List<TicketEntity> =
        ticketCache.getBookMarkedTickets()

    override suspend fun setTicketBookmarked(ticketId: Long): Int =
        ticketCache.setTicketBookmarked(ticketId)

    override suspend fun setTicketUnBookMarked(ticketId: Long): Int =
        ticketCache.setTicketUnBookMarked(ticketId)

    override suspend fun isCached(): Boolean =
        ticketCache.isCached()
}