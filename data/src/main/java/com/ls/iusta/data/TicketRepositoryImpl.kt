package com.ls.iusta.data

import com.ls.iusta.data.mapper.category.CategoryInfoMapper
import com.ls.iusta.data.mapper.category.CategoryMapper
import com.ls.iusta.data.mapper.ticket.ShortTicketMapper
import com.ls.iusta.data.mapper.ticket.TicketMapper
import com.ls.iusta.data.mapper.worker.RatingMapper
import com.ls.iusta.data.mapper.worker.WorkerMapper
import com.ls.iusta.data.source.TicketDataSourceFactory
import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.tickets.ShortTicket
import com.ls.iusta.domain.models.tickets.Ticket
import com.ls.iusta.domain.models.worker.Rating
import com.ls.iusta.domain.models.worker.Worker
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketDataSourceFactory: TicketDataSourceFactory,
    private val ticketMapper: TicketMapper,
    private val shortTicketMapper: ShortTicketMapper,
    private val categoryInfoMapper: CategoryInfoMapper,
    private val workerMapper: WorkerMapper,
    private val ratingMapper: RatingMapper
) : TicketRepository {
    override suspend fun getTickets(
        ticket_status: String,
        auth_token: String?
    ): Flow<List<Ticket>> =
        flow {
            val isCached = ticketDataSourceFactory.getCacheDataSource().isCached()
            val ticketList =
                ticketDataSourceFactory.getDataStore(isCached).getTickets(ticket_status, auth_token)
                    .map { ticketEntity ->
                        ticketMapper.mapFromEntity(ticketEntity)
                    }
            saveTickets(ticketList)
            emit(ticketList)
        }

    override suspend fun getTicket(
        ticket_status: String,
        auth_token: String?,
        ticketId: Long
    ): Flow<Ticket> = flow {
        var ticket = ticketDataSourceFactory.getCacheDataSource()
            .getTicket(ticket_status, auth_token, ticketId)
        if (ticket.title.isEmpty()) {
            ticket = ticketDataSourceFactory.getRemoteDataSource()
                .getTicket(ticket_status, auth_token, ticketId)
        }

        emit(ticketMapper.mapFromEntity(ticket))
    }

    override suspend fun categories(menu_id: Int, auth_token: String?): Flow<CategoryInfo> =
        flow {
            val categoryInfo = ticketDataSourceFactory.getRemoteDataSource().categories(menu_id, auth_token)
            emit(categoryInfoMapper.mapFromEntity(categoryInfo))
        }

    override suspend fun createTicket(category_id: Int, auth_token: String?): Flow<ShortTicket> =
        flow {
            val ticket =
                ticketDataSourceFactory.getRemoteDataSource().createTicket(category_id, auth_token)
            emit(shortTicketMapper.mapFromEntity(ticket))
        }

    override suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): Flow<ShortTicket> = flow {
        val ticket = ticketDataSourceFactory.getRemoteDataSource()
            .addNoteTicket(ticketId, ticketNote, auth_token)
        emit(shortTicketMapper.mapFromEntity(ticket))
    }

    override suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): Flow<String> = flow {
        val note = ticketDataSourceFactory.getRemoteDataSource()
            .getNoteTicket(ticketId, ticketNote, auth_token)
        emit(note)
    }

    override suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String,
        auth_token: String?
    ): Flow<Boolean> = flow {
        val rating = ticketDataSourceFactory.getRemoteDataSource()
            .addRating(workerRating, ticketId, ticketNote, auth_token)
        emit(rating)
    }

    override suspend fun getRating(ticketId: Long, auth_token: String?): Flow<Rating> = flow {
        val rating = ticketDataSourceFactory.getRemoteDataSource().getRating(ticketId, auth_token)
        emit(ratingMapper.mapFromEntity(rating))
    }

    override suspend fun worker(id: Int, auth_token: String?): Flow<Worker> = flow {
        val worker = ticketDataSourceFactory.getRemoteDataSource().worker(id, auth_token)
        emit(workerMapper.mapFromEntity(worker))
    }

    override suspend fun saveTickets(listTickets: List<Ticket>) {
        val ticketEntities = listTickets.map { ticket ->
            ticketMapper.mapToEntity(ticket)
        }
        ticketDataSourceFactory.getCacheDataSource()
            .saveTicket(ticketEntities)
    }

    override suspend fun getBookMarkedTickets(): Flow<List<Ticket>> = flow {
        val bookMarkedTicket =
            ticketDataSourceFactory.getCacheDataSource().getBookMarkedTickets()
                .map { ticketEntity ->
                    ticketMapper.mapFromEntity(ticketEntity)
                }

        emit(bookMarkedTicket)
    }

    override suspend fun setTicketBookmarked(ticketId: Long): Flow<Int> = flow {
        emit(ticketDataSourceFactory.getCacheDataSource().setTicketBookmarked(ticketId))
    }

    override suspend fun setTicketUnBookMarked(ticketId: Long): Flow<Int> = flow {
        emit(ticketDataSourceFactory.getCacheDataSource().setTicketUnBookMarked(ticketId))
    }
}
