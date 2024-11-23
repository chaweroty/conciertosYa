package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.OurEventsDTO;
import com.chaweDev.conciertosYa.entity.OurArtists;
import com.chaweDev.conciertosYa.entity.OurEvents;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.repository.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventManagementService {

    @Autowired
    private EventRepo eventRepo;

    public OurEventsDTO addEvent(OurEventsDTO event) {
        OurEventsDTO response = new OurEventsDTO();
        try {
            OurEvents savedEvent = new OurEvents();

            saveEvent(savedEvent, event.getName(), event.getDate(), event.getHour(), event.getDescription(), event.getMusicalGenre(), event.getStatus(), event.getImage(), event.getPlace(), event.getArtist());

            OurEvents ourEventResult = eventRepo.save(savedEvent);
            if (savedEvent.getId() > 0) {
                response.setOurEvents(ourEventResult);
                response.setMessage("Event Saved Successfully");
                response.setStatusCode(200);
            } else {
                response.setMessage("Event not saved due to an unknown error.");
                response.setStatusCode(500);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurEventsDTO getAllEvents() {
        OurEventsDTO response = new OurEventsDTO();
        try {
            List<OurEvents> events = eventRepo.findAll();
            if (events.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No events found");
            } else {
                response.setStatusCode(200);
                response.setOurEventsList(events);
                response.setMessage("Events found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurEventsDTO getEventById(Integer eventId) {
        OurEventsDTO response = new OurEventsDTO();
        try {
            OurEvents event = eventRepo.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
            response.setStatusCode(200);
            response.setOurEvents(event);
            response.setMessage("Event found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurEventsDTO updateEvent(Integer eventId, OurEvents event) {
        OurEventsDTO response = new OurEventsDTO();
        try {
            Optional<OurEvents> existingEventOpt = eventRepo.findById(eventId);
            if (existingEventOpt.isPresent()) {
                OurEvents existingEvent = updateExistingEvent(event, existingEventOpt);

                OurEvents updatedEvent = eventRepo.save(existingEvent);
                response.setStatusCode(200);
                response.setOurEvents(updatedEvent);
                response.setMessage("Event updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Event not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    private static OurEvents updateExistingEvent(OurEvents event, Optional<OurEvents> existingEventOpt) {
        OurEvents existingEvent = existingEventOpt.get();
        saveEvent(existingEvent, event.getName(), event.getDate(), event.getHour(), event.getDescription(), event.getMusicalGenre(), event.getStatus(), event.getImage(), event.getPlace(), event.getArtist());
        return existingEvent;
    }

    private static void saveEvent(OurEvents existingEvent, String name, LocalDate date, LocalTime hour, String description, String musicalGenre, String status, String image, OurPlaces place, OurArtists artist) {
        existingEvent.setName(name);
        existingEvent.setDate(date);
        existingEvent.setHour(hour);
        existingEvent.setDescription(description);
        existingEvent.setMusicalGenre(musicalGenre);
        existingEvent.setStatus(status);
        existingEvent.setImage(image);
        existingEvent.setPlace(place);
        existingEvent.setArtist(artist);
    }

    public OurEventsDTO deleteEvent(Integer eventId) {
        OurEventsDTO response = new OurEventsDTO();
        try {
            Optional<OurEvents> eventOptional = eventRepo.findById(eventId);
            if (eventOptional.isPresent()) {
                eventRepo.deleteById(eventId);
                response.setStatusCode(200);
                response.setMessage("Event deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Event not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting event: " + e.getMessage());
        }
        return response;
    }
}