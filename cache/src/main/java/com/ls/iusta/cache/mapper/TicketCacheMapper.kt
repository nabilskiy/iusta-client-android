package com.ls.iusta.cache.mapper

import com.ls.iusta.cache.models.TicketCacheEntity
import com.ls.iusta.data.models.ticket.TicketEntity
import javax.inject.Inject

class TicketCacheMapper @Inject constructor(
) : CacheMapper<TicketCacheEntity, TicketEntity> {

    override fun mapFromCached(model: TicketCacheEntity): TicketEntity {
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
            category_name = model.category_name,
            category_icon = model.category_icon,
            ticket_priority_name = model.ticket_priority_name,
            ticket_priority_value_en = model.ticket_priority_value_en,
            next_event_label = model.next_event_label,
            next_event_id = model.next_event_id,
            next_event_name = model.next_event_name,
            current_event_label = model.current_event_label,
            current_event_id = model.current_event_id
        )
    }

    override fun mapToCached(model: TicketEntity): TicketCacheEntity {
        return TicketCacheEntity(
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
            category_name = model.category_name,
            category_icon = model.category_icon,
            ticket_priority_name = model.ticket_priority_name,
            ticket_priority_value_en = model.ticket_priority_value_en,
            next_event_label = model.next_event_label,
            next_event_id = model.next_event_id,
            next_event_name = model.next_event_name,
            current_event_label = model.current_event_label,
            current_event_id = model.current_event_id
        )
    }
}
