package com.ls.iusta.data

import com.ls.iusta.data.mapper.TicketMapper
import com.ls.iusta.data.source.TicketDataSourceFactory
import com.ls.iusta.domain.models.Ticket
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketDataSourceFactory: TicketDataSourceFactory,
    private val ticketMapper: TicketMapper
) : TicketRepository {
    override suspend fun getTickets(): Flow<List<Ticket>> = flow {
        val isCached = ticketDataSourceFactory.getCacheDataSource().isCached()
        val ticketList = ticketDataSourceFactory.getDataStore(isCached).getTickets()
            .map { ticketEntity ->
                ticketMapper.mapFromEntity(ticketEntity)
            }
        saveTickets(ticketList)
        emit(ticketList)
    }

    override suspend fun getTicket(ticketId: Long): Flow<Ticket> = flow {
        var ticket = ticketDataSourceFactory.getCacheDataSource().getTicket(ticketId)
        if (ticket.image.isEmpty()) {
            ticket = ticketDataSourceFactory.getRemoteDataSource().getTicket(ticketId)
        }

        emit(ticketMapper.mapFromEntity(ticket))
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
