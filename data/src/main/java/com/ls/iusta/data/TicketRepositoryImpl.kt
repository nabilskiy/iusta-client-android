package com.ls.iusta.data

import com.ls.iusta.data.mapper.category.CategoryInfoMapper
import com.ls.iusta.data.mapper.category.CategoryMapper
import com.ls.iusta.data.mapper.ticket.*
import com.ls.iusta.data.mapper.worker.RatingMapper
import com.ls.iusta.data.mapper.worker.WorkerMapper
import com.ls.iusta.data.models.ticket.AttachmentFileEntity
import com.ls.iusta.data.source.TicketDataSourceFactory
import com.ls.iusta.domain.models.category.CategoryInfo
import com.ls.iusta.domain.models.tickets.*
import com.ls.iusta.domain.models.worker.Rating
import com.ls.iusta.domain.models.worker.Worker
import com.ls.iusta.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketDataSourceFactory: TicketDataSourceFactory,
    private val getTicketMapper: GetTicketMapper,
    private val shortTicketMapper: ShortTicketMapper,
    private val createTicketMapper: CreateTicketMapper,
    private val categoryInfoMapper: CategoryInfoMapper,
    private val workerMapper: WorkerMapper,
    private val ratingMapper: RatingMapper,
    private val attachmentFileMapper: AttachmentFileMapper
) : TicketRepository {
    override suspend fun getTickets(
        ticket_status: Boolean,
        pageNumber: Int?
    ): Flow<GetTicket> =
        flow {
            val ticketList =
                ticketDataSourceFactory.getRemoteDataSource()
                    .   getTickets(ticket_status, ticketDataSourceFactory.getAuthToken(), pageNumber)
            emit(getTicketMapper.mapFromEntity(ticketList))
        }

    override suspend fun getTicket(
        ticketId: Long
    ): Flow<GetTicket> = flow {
        var ticket = ticketDataSourceFactory.getRemoteDataSource()
            .getTicket(ticketDataSourceFactory.getAuthToken(), ticketId)
        emit(getTicketMapper.mapFromEntity(ticket))
    }

    override suspend fun categories(menu_id: Int): Flow<CategoryInfo> =
        flow {
            val categoryInfo =
                ticketDataSourceFactory.getRemoteDataSource()
                    .categories(menu_id, ticketDataSourceFactory.getAuthToken())
            emit(categoryInfoMapper.mapFromEntity(categoryInfo))
        }

    override suspend fun createTicket(
        attachments: List<AttachmentFile>,
        category_id: Int,
        note: String?
    ): Flow<CreateTicket> =
        flow {
            val ticket =
                ticketDataSourceFactory.getRemoteDataSource()
                    .createTicket(
                        attachments.map { attachmentFileMapper.mapToEntity(it) },
                        category_id, note, ticketDataSourceFactory.getAuthToken()
                    )
            emit(createTicketMapper.mapFromEntity(ticket))
        }

    override suspend fun addNoteTicket(
        ticketId: Long,
        ticketNote: String?
    ): Flow<ShortTicket> = flow {
        val ticket = ticketDataSourceFactory.getRemoteDataSource()
            .addNoteTicket(ticketId, ticketNote, ticketDataSourceFactory.getAuthToken())
        emit(shortTicketMapper.mapFromEntity(ticket))
    }

    override suspend fun getNoteTicket(
        ticketId: Long,
        ticketNote: String?
    ): Flow<String> = flow {
        val note = ticketDataSourceFactory.getRemoteDataSource()
            .getNoteTicket(ticketId, ticketNote, ticketDataSourceFactory.getAuthToken())
        emit(note)
    }

    override suspend fun addRating(
        workerRating: Int,
        ticketId: Long,
        ticketNote: String?
    ): Flow<Boolean> = flow {
        val rating = ticketDataSourceFactory.getRemoteDataSource()
            .addRating(workerRating, ticketId, ticketNote, ticketDataSourceFactory.getAuthToken())
        emit(rating)
    }

    override suspend fun getRating(ticketId: Long): Flow<Rating> = flow {
        val rating = ticketDataSourceFactory.getRemoteDataSource()
            .getRating(ticketId, ticketDataSourceFactory.getAuthToken())
        emit(ratingMapper.mapFromEntity(rating))
    }

    override suspend fun worker(id: Int): Flow<Worker> = flow {
        val worker = ticketDataSourceFactory.getRemoteDataSource()
            .worker(id, ticketDataSourceFactory.getAuthToken())
        emit(workerMapper.mapFromEntity(worker))
    }

}
