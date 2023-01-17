package com.ls.iusta.remote.api

import com.ls.iusta.remote.models.BaseModel
import com.ls.iusta.remote.models.category.CategoryResponseModel
import com.ls.iusta.remote.models.push.PushResponseModel
import com.ls.iusta.remote.models.ticket.CreateTicketResponseModel
import com.ls.iusta.remote.models.ticket.TicketNoteResponseModel
import com.ls.iusta.remote.models.ticket.TicketResponseModel
import com.ls.iusta.remote.models.worker.RatingResponseModel
import com.ls.iusta.remote.models.worker.WorkerResponseModel
import okhttp3.MultipartBody
import retrofit2.http.*

interface TicketService {

    @FormUrlEncoded
    @POST(Endpoints.CATEGORIES)
    suspend fun categories(
        @Field("menu_id") menu_id: Int,
        @Field("auth_token") auth_token: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): CategoryResponseModel

   // @Multipart
    @POST(Endpoints.TICKET_CREATE)
    suspend fun createTicket(
        @Part attachments: List<MultipartBody.Part> = emptyList(),
        @Query("category_id") category_id: Int,
        @Query("note") note: String?,
        @Query("auth_token") auth_token: String?,
        @Query("secret_key") secret_key: String = SECRET_KEY
    ): CreateTicketResponseModel

    @FormUrlEncoded
    @POST(Endpoints.TICKET_HISTORY)
    suspend fun getTickets(
        @Field("auth_token") auth_token: String?,
        @Field("page_number") pageNumber: Int?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): TicketResponseModel

    @FormUrlEncoded
    @POST(Endpoints.TICKET_ACTIVE)
    suspend fun getActiveTickets(
        @Field("auth_token") auth_token: String?,
        @Field("page_number") pageNumber: Int?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): TicketResponseModel

    @FormUrlEncoded
    @POST(Endpoints.TICKET_HISTORY)
    suspend fun getTicket(
        @Field("auth_token") auth_token: String?,
        @Field("ticket_id") id: Long,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): TicketResponseModel

    @FormUrlEncoded
    @POST(Endpoints.TICKET_NOTE)
    suspend fun ticketNote(
        @Field("ticket_id") id: Long,
        @Field("ticket_note") ticket_note: String?,
        @Field("auth_token") query: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): CreateTicketResponseModel

    @FormUrlEncoded
    @POST(Endpoints.TICKET_NOTE_INFO)
    suspend fun ticketNoteInfo(
        @Field("ticket_id") id: Long,
        @Field("ticket_note") ticket_note: String?,
        @Field("auth_token") query: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): TicketNoteResponseModel

    @FormUrlEncoded
    @POST(Endpoints.WORKER_RATING)
    suspend fun addRating(
        @Field("worker_rating") rating: Int,
        @Field("ticket_id") id: Long,
        @Field("ticket_note") ticket_note: String?,
        @Field("auth_token") query: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): BaseModel

    @FormUrlEncoded
    @POST(Endpoints.WORKER_RATING_INFO)
    suspend fun getRating(
        @Field("ticket_id") id: Long,
        @Field("auth_token") query: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): RatingResponseModel

    @FormUrlEncoded
    @POST(Endpoints.WORKER_INFO)
    suspend fun worker(
        @Field("id") id: Int,
        @Field("auth_token") query: String?,
        @Field("secret_key") secret_key: String = SECRET_KEY
    ): WorkerResponseModel

    companion object {
        private const val SECRET_KEY = "oLOk6x4FC1YCBctFTtEzhkHhkZFYJeW5xFlPZccC4mSmI5Ji"
    }

}
