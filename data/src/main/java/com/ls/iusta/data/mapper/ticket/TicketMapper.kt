package com.ls.iusta.data.mapper.ticket

import com.ls.iusta.data.mapper.Mapper
import com.ls.iusta.data.models.TicketEntity
import com.ls.iusta.domain.models.tickets.Ticket
import javax.inject.Inject

class TicketMapper @Inject constructor(
) : Mapper<TicketEntity, Ticket> {

    override fun mapFromEntity(model: TicketEntity): Ticket {
        return Ticket(
            archive_flag = model.archive_flag,
            create_by = model.archive_flag,
            create_time = model.create_time,
            customer_id = model.customer_id,
            customer_user_id = model.customer_user_id,
            dynamic_category_id = model.dynamic_category_id,
            dynamic_category_name = model.dynamic_category_name,
            id = model.id,
            owner_user_name = model.owner_user_name,
            responsible_user_id = model.responsible_user_id,
            service_id = model.service_id,
            ticket_lock_id = model.ticket_lock_id,
            ticket_priority_id = model.ticket_priority_id,
            ticket_state_id = model.ticket_state_id,
            ticket_state_name = model.ticket_state_name,
            title = model.title,
            tn = model.tn,
            type_id = model.type_id,
            user_id = model.user_id,
            category_name = model.category_name
        )
    }

    override fun mapToEntity(model: Ticket): TicketEntity {
        return TicketEntity(
            archive_flag = model.archive_flag,
            create_by = model.archive_flag,
            create_time = model.create_time,
            customer_id = model.customer_id,
            customer_user_id = model.customer_user_id,
            dynamic_category_id = model.dynamic_category_id,
            dynamic_category_name = model.dynamic_category_name,
            id = model.id,
            owner_user_name = model.owner_user_name,
            responsible_user_id = model.responsible_user_id,
            service_id = model.service_id,
            ticket_lock_id = model.ticket_lock_id,
            ticket_priority_id = model.ticket_priority_id,
            ticket_state_id = model.ticket_state_id,
            ticket_state_name = model.ticket_state_name,
            title = model.title,
            tn = model.tn,
            type_id = model.type_id,
            user_id = model.user_id,
            category_name = model.category_name
        )
    }
}
