package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurSeatsDTO;
import com.chaweDev.conciertosYa.service.SeatManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Principio aplicado: Controladores RESTful
// Este controlador implementa un patrón RESTful para gestionar operaciones relacionadas con asientos,
// lo que asegura consistencia y claridad en la exposición de las API.
@RestController
@RequestMapping("/seats")
public class SeatManagementController {

    // Principio aplicado: Inversión de Dependencias (DIP)
    // Utilizamos una interfaz (ISeatManagementService) para la lógica de negocio, permitiendo flexibilidad y fácil intercambio de implementaciones.
    @Autowired
    private SeatManagementService seatManagementService;

    // Metodo para añadir un nuevo asiento.
    // Principio aplicado: Separación de Responsabilidades (SRP)
    // Este metodo delega la creación de un asiento al servicio correspondiente.
    @PostMapping("/add")
    public ResponseEntity<OurSeatsDTO> addSeat(@RequestBody OurSeatsDTO seat) {
        OurSeatsDTO response = seatManagementService.addSeat(seat);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Metodo para obtener todos los asientos disponibles.
    // Principio aplicado: Cohesión Alta
    // Mantiene su responsabilidad limitada a delegar la obtención de todos los asientos.
    @GetMapping("/get-all")
    public ResponseEntity<OurSeatsDTO> getAllSeats() {
        OurSeatsDTO response = seatManagementService.getAllSeats();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Metodo para obtener un asiento específico por su ID.
    // Principio aplicado: Abierto/Cerrado (OCP)
    // Este metodo extiende la funcionalidad de la API sin modificar su estructura básica.
    @GetMapping("/get/{seatId}")
    public ResponseEntity<OurSeatsDTO> getSeatById(@PathVariable Integer seatId) {
        return ResponseEntity.ok(seatManagementService.getSeatById(seatId));
    }

    @GetMapping("/get-place-seats/{placeId}")
    public ResponseEntity<OurSeatsDTO> getSeatPlaceById(@PathVariable Integer placeId) {
        return ResponseEntity.ok(seatManagementService.getSeatPlaceById(placeId));
    }

    // Metodo para actualizar un asiento específico.
    // Principio aplicado: Responsabilidad Única
    // Mantiene la claridad delegando la actualización al servicio.
    @PutMapping("/update/{seatId}")
    public ResponseEntity<OurSeatsDTO> updateSeat(@PathVariable Integer seatId, @RequestBody OurSeatsDTO seat) {
        return ResponseEntity.ok(seatManagementService.updateSeat(seatId, seat));
    }

    // Metodo para eliminar un asiento por su ID.
    // Principio aplicado: Reutilización de Código
    // Utiliza el servicio para eliminar, promoviendo una lógica centralizada y consistente.
    @DeleteMapping("/delete/{seatId}")
    public ResponseEntity<OurSeatsDTO> deleteSeat(@PathVariable Integer seatId) {
        return ResponseEntity.ok(seatManagementService.deleteSeat(seatId));
    }
}