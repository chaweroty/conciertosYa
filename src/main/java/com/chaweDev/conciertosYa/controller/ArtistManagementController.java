package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurArtistsDTO;
import com.chaweDev.conciertosYa.entity.OurArtists;
import com.chaweDev.conciertosYa.service.ArtistManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
public class ArtistManagementController {

    @Autowired
    private ArtistManagementService artistManagementService;

    @PostMapping("/add")
    public ResponseEntity<OurArtistsDTO> addPlace(@RequestBody OurArtistsDTO artist) {
        OurArtistsDTO response = artistManagementService.addArtist(artist);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<OurArtistsDTO> getAllPlaces() {
        OurArtistsDTO response = artistManagementService.getAllArtists();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{artistId}")
    public ResponseEntity<OurArtistsDTO> getPlaceById(@PathVariable Integer artistId) {
        return ResponseEntity.ok(artistManagementService.getArtistById(artistId));
    }

    @PutMapping("/update/{artistId}")
    public ResponseEntity<OurArtistsDTO> updatePlace(@PathVariable Integer artistId, @RequestBody OurArtists artist) {
        return ResponseEntity.ok(artistManagementService.updateArtist(artistId, artist));
    }

    @DeleteMapping("/delete/{artistId}")
    public ResponseEntity<OurArtistsDTO> deletePlace(@PathVariable Integer artistId) {
        return ResponseEntity.ok(artistManagementService.deleteArtist(artistId));
    }
}