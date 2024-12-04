package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurTicketsDTO;
import com.chaweDev.conciertosYa.entity.OurTickets;
import com.chaweDev.conciertosYa.service.TicketManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Principio aplicado: Controladores RESTful
// Este controlador sigue el patrón REST para manejar solicitudes HTTP relacionadas con boletos.
// El uso de @RestController y @RequestMapping garantiza que las rutas estén organizadas y fáciles de entender,
// aplicando el principio de diseño de interfaces claras y consistentes.
@RestController
@RequestMapping("/tickets")

public class TicketManagementController {

    // Principio aplicado: Inversión de Dependencias (DIP)
    // Aquí se aplica la inversión de dependencias al inyectar una interfaz (ITicketManagementService)
    // en lugar de una implementación concreta. Esto facilita el cambio de implementación en el futuro y
    // mejora la testabilidad de este controlador.
    @Autowired
    private TicketManagementService ticketManagementService;

    // Principio aplicado: Separación de Responsabilidades (SRP)
    // Cada metodo solo maneja una solicitud HTTP y delega toda la lógica de negocio al servicio.
    // Esto evita que el controlador tenga múltiples responsabilidades, manteniéndolo limpio y fácil de mantener.
    @PostMapping("/add")
    public ResponseEntity<OurTicketsDTO> addTicket(@RequestBody OurTicketsDTO ticket) {
        OurTicketsDTO response = ticketManagementService.addTicket(ticket);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: DRY (Don't Repeat Yourself)
    // Este metodo usa el servicio para obtener todos los boletos sin duplicar la lógica de negocio.
    @GetMapping("/get-all")
    public ResponseEntity<OurTicketsDTO> getAllTickets() {
        OurTicketsDTO response = ticketManagementService.getAllTickets();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: Abierto/Cerrado (OCP)
    // Este metodo es extensible para manejar diferentes tipos de consultas por ID sin modificar su estructura,
    // siempre y cuando el servicio gestione adecuadamente la lógica interna.
    @GetMapping("/get/{ticketId}")
    public ResponseEntity<OurTicketsDTO> getTicketById(@PathVariable Integer ticketId) {
        return ResponseEntity.ok(ticketManagementService.getTicketById(ticketId));
    }

    // Principio aplicado: Cohesión Alta
    // Este metodo es específico para actualizar un boleto, manteniendo su responsabilidad clara y única.
    // Se delega la lógica de actualización al servicio, manteniendo la cohesión del controlador.
    @PutMapping("/update/{ticketId}")
    public ResponseEntity<OurTicketsDTO> updateTicket(@PathVariable Integer ticketId, @RequestBody OurTickets ticket) {
        return ResponseEntity.ok(ticketManagementService.updateTicket(ticketId, ticket));
    }

    // Metodo para eliminar un ticket por su ID.
    // Principio aplicado: Reutilización de Código
    // Utiliza el servicio para eliminar, promoviendo una lógica centralizada y consistente.
    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<OurTicketsDTO> deleteTicket(@PathVariable Integer ticketId) {
        return ResponseEntity.ok(ticketManagementService.deleteTicket(ticketId));
    }
}
