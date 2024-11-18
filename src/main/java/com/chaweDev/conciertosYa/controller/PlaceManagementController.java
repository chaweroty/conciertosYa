package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurPlacesDTO;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.service.PlaceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/places")
public class PlaceManagementController {

    @Autowired
    private PlaceManagementService placeManagementService;

    @PostMapping("/add")
    public ResponseEntity<OurPlacesDTO> addPlace(@RequestBody OurPlacesDTO place) {
        OurPlacesDTO response = placeManagementService.addPlace(place);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<OurPlacesDTO> getAllPlaces() {
        OurPlacesDTO response = placeManagementService.getAllPlaces();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{placeId}")
    public ResponseEntity<OurPlacesDTO> getPlaceById(@PathVariable Integer placeId) {
        return ResponseEntity.ok(placeManagementService.getPlaceById(placeId));
    }

    @PutMapping("/update/{placeId}")
    public ResponseEntity<OurPlacesDTO> updatePlace(@PathVariable Integer placeId, @RequestBody OurPlaces place) {
        return ResponseEntity.ok(placeManagementService.updatePlace(placeId, place));
    }

    @DeleteMapping("/delete/{placeId}")
    public ResponseEntity<OurPlacesDTO> deletePlace(@PathVariable Integer placeId) {
        return ResponseEntity.ok(placeManagementService.deletePlace(placeId));
    }
}