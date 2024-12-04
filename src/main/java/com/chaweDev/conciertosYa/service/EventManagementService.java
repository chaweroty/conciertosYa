package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.DTO;
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

    /*
    Instanciación de OurEventsDTO desde un objeto de tipo DTO:
    El metodo addEvent recibe un parámetro de tipo DTO, que podría ser cualquier objeto que implemente la interfaz DTO.
    La expresión instanceof OurEventsDTO verifica si el objeto dto es una instancia de OurEventsDTO,
    lo que permite que se pueda tratar específicamente como un objeto de tipo OurEventsDTO. Si el objeto es de ese tipo,
    el código dentro del bloque if se ejecuta.

    Asignación a una variable del tipo OurEventsDTO:
    El uso de instanceof no solo verifica el tipo del objeto,
    sino que también realiza un casting implícito del objeto dto a OurEventsDTO.
    Esto significa que el objeto dto es tratado como un OurEventsDTO dentro del bloque if.
    Esta técnica permite que se puedan acceder de manera directa a los métodos y propiedades específicos de OurEventsDTO sin necesidad de hacer un cast manual,
    y es posible gracias al polimorfismo.

    Utilización de datos para crear y guardar un evento:
    Una vez verificado y casteado el DTO como OurEventsDTO, se procede a crear un objeto de tipo OurEvents
    y a establecer sus valores mediante los datos provenientes del DTO. Luego, el evento se guarda en el repositorio mediante eventRepo.save().
    Si el evento se guarda exitosamente (es decir, si el id del evento es mayor a 0), se actualizan los valores de la respuesta y se indica que el evento fue guardado correctamente.
    En caso contrario, se devuelve un mensaje indicando un error en el proceso de guardado.
    */
    public OurEventsDTO addEvent(DTO dto) {
        try {
            if (dto instanceof OurEventsDTO event) {
                OurEvents savedEvent = new OurEvents();

                saveEvent(savedEvent, event.getName(), event.getDate(), event.getHour(), event.getDescription(),
                        event.getMusicalGenre(), event.getStatus(), event.getImage(), event.getPlace(), event.getArtist());

                OurEvents ourEventResult = eventRepo.save(savedEvent);

                if (ourEventResult.getId() > 0) {
                    return new OurEventsDTO.Builder()
                            .ourEvents(ourEventResult)
                            .message("Event Saved Successfully")
                            .statusCode(200)
                            .build();
                } else {
                    return new OurEventsDTO.Builder()
                            .message("Event not saved due to an unknown error.")
                            .statusCode(500)
                            .build();
                }
            } else {
                return new OurEventsDTO.Builder()
                        .message("Invalid DTO type")
                        .statusCode(400)
                        .build();
            }
        } catch (Exception e) {
            return new OurEventsDTO.Builder()
                    .statusCode(500)
                    .message("Error occurred: " + e.getMessage())
                    .build();
        }
    }

    public OurEventsDTO getAllEvents() {
        try {
            List<OurEvents> events = eventRepo.findAll();
            if (events.isEmpty()) {
                return new OurEventsDTO.Builder()
                        .statusCode(404)
                        .message("No events found")
                        .build();
            } else {
                return new OurEventsDTO.Builder()
                        .statusCode(200)
                        .ourEventsList(events)
                        .message("Events found successfully")
                        .build();
            }
        } catch (Exception e) {
            return new OurEventsDTO.Builder()
                    .statusCode(500)
                    .message("Error occurred: " + e.getMessage())
                    .build();
        }
    }

    public OurEventsDTO getEventById(Integer eventId) {
        try {
            OurEvents event = eventRepo.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            return new OurEventsDTO.Builder()
                    .statusCode(200)
                    .ourEvents(event)
                    .message("Event found successfully")
                    .build();
        } catch (Exception e) {
            return new OurEventsDTO.Builder()
                    .statusCode(500)
                    .message("Error occurred: " + e.getMessage())
                    .build();
        }
    }

    public OurEventsDTO updateEvent(Integer eventId, OurEvents event) {
        try {
            Optional<OurEvents> existingEventOpt = eventRepo.findById(eventId);
            if (existingEventOpt.isPresent()) {
                OurEvents existingEvent = updateExistingEvent(event, existingEventOpt);

                OurEvents updatedEvent = eventRepo.save(existingEvent);
                return new OurEventsDTO.Builder()
                        .statusCode(200)
                        .ourEvents(updatedEvent)
                        .message("Event updated successfully")
                        .build();
            } else {
                return new OurEventsDTO.Builder()
                        .statusCode(404)
                        .message("Event not found")
                        .build();
            }
        } catch (Exception e) {
            return new OurEventsDTO.Builder()
                    .statusCode(500)
                    .message("Error occurred: " + e.getMessage())
                    .build();
        }
    }

    private static OurEvents updateExistingEvent(OurEvents event, Optional<OurEvents> existingEventOpt) {
        OurEvents existingEvent = existingEventOpt.get();
        saveEvent(existingEvent, event.getName(), event.getDate(), event.getHour(), event.getDescription(),
                event.getMusicalGenre(), event.getStatus(), event.getImage(), event.getPlace(), event.getArtist());
        return existingEvent;
    }

    private static void saveEvent(OurEvents existingEvent, String name, LocalDate date, LocalTime hour,
                                  String description, String musicalGenre, String status, String image,
                                  OurPlaces place, OurArtists artist) {
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
        try {
            Optional<OurEvents> eventOptional = eventRepo.findById(eventId);
            if (eventOptional.isPresent()) {
                eventRepo.deleteById(eventId);
                return new OurEventsDTO.Builder()
                        .statusCode(200)
                        .message("Event deleted successfully")
                        .build();
            } else {
                return new OurEventsDTO.Builder()
                        .statusCode(404)
                        .message("Event not found")
                        .build();
            }
        } catch (Exception e) {
            return new OurEventsDTO.Builder()
                    .statusCode(500)
                    .message("Error occurred while deleting event: " + e.getMessage())
                    .build();
        }
    }
}