package com.chaweDev.conciertosYa.service.visual;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurEventsDTO;
import com.chaweDev.conciertosYa.entity.OurEvents;

public interface IEventManagementService {
    OurEventsDTO addEvent(DTO event);

    OurEventsDTO getAllEvents();

    OurEventsDTO getEventById(Integer eventId);

    OurEventsDTO updateEvent(Integer eventId, OurEvents event);

    OurEventsDTO deleteEvent(Integer eventId);
}
