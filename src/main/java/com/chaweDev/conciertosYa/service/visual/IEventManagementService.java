package com.chaweDev.conciertosYa.service.Visual;

import com.chaweDev.conciertosYa.dto.OurEventsDTO;
import com.chaweDev.conciertosYa.entity.OurEvents;

public interface IEventManagementService {
    OurEventsDTO addEvent(OurEventsDTO event);

    OurEventsDTO getAllEvents();

    OurEventsDTO getEventById(Integer eventId);

    OurEventsDTO updateEvent(Integer eventId, OurEvents event);

    OurEventsDTO deleteEvent(Integer eventId);
}
