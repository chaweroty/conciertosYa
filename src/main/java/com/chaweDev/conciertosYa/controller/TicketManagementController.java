package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurTicketsDTO;
import com.chaweDev.conciertosYa.entity.OurTickets;
import com.chaweDev.conciertosYa.service.TicketManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")

public class TicketManagementController {

    @Autowired
    private TicketManagementService ticketManagementService;

    @PostMapping("/add")
    public ResponseEntity<OurTicketsDTO> addTicket(@RequestBody OurTicketsDTO ticket) {
        OurTicketsDTO response = ticketManagementService.addTicket(ticket);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<OurTicketsDTO> getAllTickets() {
        OurTicketsDTO response = ticketManagementService.getAllTickets();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{ticketId}")
    public ResponseEntity<OurTicketsDTO> getTicketById(@PathVariable Integer ticketId) {
        return ResponseEntity.ok(ticketManagementService.getTicketById(ticketId));
    }

    @PutMapping("/update/{ticketId}")
    public ResponseEntity<OurTicketsDTO> updateTicket(@PathVariable Integer ticketId, @RequestBody OurTickets ticket) {
        return ResponseEntity.ok(ticketManagementService.updateTicket(ticketId, ticket));
    }

    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<OurTicketsDTO> deleteTicket(@PathVariable Integer ticketId) {
        return ResponseEntity.ok(ticketManagementService.deleteTicket(ticketId));
    }
}
