package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurEventsDTO;
import com.chaweDev.conciertosYa.entity.OurEvents;
import com.chaweDev.conciertosYa.service.EventManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventManagementController {

    @Autowired
    private EventManagementService eventManagementService;

    @PostMapping("/add")
    public ResponseEntity<OurEventsDTO> addEvent(@RequestBody OurEventsDTO event) {
        OurEventsDTO response = eventManagementService.addEvent(event);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<OurEventsDTO> getAllEvents() {
        OurEventsDTO response = eventManagementService.getAllEvents();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{eventId}")
    public ResponseEntity<OurEventsDTO> getEventById(@PathVariable Integer eventId) {
        return ResponseEntity.ok(eventManagementService.getEventById(eventId));
    }

    @PutMapping("/update/{eventId}")
    public ResponseEntity<OurEventsDTO> updateEvent(@PathVariable Integer eventId, @RequestBody OurEvents event) {
        return ResponseEntity.ok(eventManagementService.updateEvent(eventId, event));
    }

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<OurEventsDTO> deleteEvent(@PathVariable Integer eventId) {
        return ResponseEntity.ok(eventManagementService.deleteEvent(eventId));
    }
}