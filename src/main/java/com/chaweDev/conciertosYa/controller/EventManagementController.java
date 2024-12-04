package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.service.visual.IEventManagementService;
import com.chaweDev.conciertosYa.dto.OurEventsDTO;
import com.chaweDev.conciertosYa.entity.OurEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Principio aplicado: Controladores RESTful
// Este controlador sigue el patrón REST para manejar solicitudes HTTP relacionadas con eventos.
// El uso de @RestController y @RequestMapping garantiza que las rutas estén organizadas y fáciles de entender,
// aplicando el principio de diseño de interfaces claras y consistentes.
@RestController
@RequestMapping("/events")
public class EventManagementController {

    // Principio aplicado: Inversión de Dependencias (DIP)
    // La interfaz IEventManagementService se inyecta en lugar de una implementación concreta,
    // permitiendo flexibilidad y facilidad de pruebas al desacoplar la lógica de negocio del controlador.
    @Autowired
    private IEventManagementService eventManagementService;

    // Principio aplicado: Separación de Responsabilidades (SRP)
    // Este metodo maneja la creación de un nuevo evento delegando toda la lógica al servicio.
    @PostMapping("/add")
    public ResponseEntity<OurEventsDTO> addEvent(@RequestBody OurEventsDTO event) {
        OurEventsDTO response = eventManagementService.addEvent(event);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: DRY (Don't Repeat Yourself)
    // Este metodo utiliza el servicio para obtener todos los eventos sin duplicar la lógica de negocio.
    @GetMapping("/get-all")
    public ResponseEntity<OurEventsDTO> getAllEvents() {
        OurEventsDTO response = eventManagementService.getAllEvents();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: Abierto/Cerrado (OCP)
    // El metodo está diseñado para ser extensible y manejar diferentes casos de obtención de eventos sin necesidad de modificar el controlador.
    @GetMapping("/get/{eventId}")
    public ResponseEntity<OurEventsDTO> getEventById(@PathVariable Integer eventId) {
        return ResponseEntity.ok(eventManagementService.getEventById(eventId));
    }

    // Principio aplicado: Cohesión Alta
    // Este metodo actualiza un evento específico, delegando la lógica de actualización al servicio,
    // lo que mantiene la cohesión y claridad del controlador.
    @PutMapping("/update/{eventId}")
    public ResponseEntity<OurEventsDTO> updateEvent(@PathVariable Integer eventId, @RequestBody OurEvents event) {
        return ResponseEntity.ok(eventManagementService.updateEvent(eventId, event));
    }

    // Principio aplicado: Responsabilidad Única
    // Este metodo está diseñado exclusivamente para manejar la eliminación de un evento,
    // asegurando que el controlador permanezca enfocado y claro.
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<OurEventsDTO> deleteEvent(@PathVariable Integer eventId) {
        return ResponseEntity.ok(eventManagementService.deleteEvent(eventId));
    }
}