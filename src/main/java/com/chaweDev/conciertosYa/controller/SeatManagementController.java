package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurSeatsDTO;
import com.chaweDev.conciertosYa.service.SeatManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatManagementController {

    @Autowired
    private SeatManagementService seatManagementService;

    @PostMapping("/add")
    public ResponseEntity<OurSeatsDTO> addSeat(@RequestBody OurSeatsDTO seat) {
        OurSeatsDTO response = seatManagementService.addSeat(seat);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<OurSeatsDTO> getAllSeats() {
        OurSeatsDTO response = seatManagementService.getAllSeats();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{seatId}")
    public ResponseEntity<OurSeatsDTO> getSeatById(@PathVariable Integer seatId) {
        return ResponseEntity.ok(seatManagementService.getSeatById(seatId));
    }

    @GetMapping("/get-place-seats/{placeId}")
    public ResponseEntity<OurSeatsDTO> getSeatPlaceById(@PathVariable Integer placeId) {
        return ResponseEntity.ok(seatManagementService.getSeatPlaceById(placeId));
    }

    @PutMapping("/update/{seatId}")
    public ResponseEntity<OurSeatsDTO> updateSeat(@PathVariable Integer seatId, @RequestBody OurSeatsDTO seat) {
        return ResponseEntity.ok(seatManagementService.updateSeat(seatId, seat));
    }

    @DeleteMapping("/delete/{seatId}")
    public ResponseEntity<OurSeatsDTO> deleteSeat(@PathVariable Integer seatId) {
        return ResponseEntity.ok(seatManagementService.deleteSeat(seatId));
    }
}