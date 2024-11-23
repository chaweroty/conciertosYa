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
    public ResponseEntity<OurArtistsDTO> addArtist(@RequestBody OurArtistsDTO artist) {
        OurArtistsDTO response = artistManagementService.addArtist(artist);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<OurArtistsDTO> getAllArtists() {
        OurArtistsDTO response = artistManagementService.getAllArtists();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{artistId}")
    public ResponseEntity<OurArtistsDTO> getArtistById(@PathVariable Integer artistId) {
        return ResponseEntity.ok(artistManagementService.getArtistById(artistId));
    }

    @PutMapping("/update/{artistId}")
    public ResponseEntity<OurArtistsDTO> updateArtist(@PathVariable Integer artistId, @RequestBody OurArtists artist) {
        return ResponseEntity.ok(artistManagementService.updateArtist(artistId, artist));
    }

    @DeleteMapping("/delete/{artistId}")
    public ResponseEntity<OurArtistsDTO> deleteArtist(@PathVariable Integer artistId) {
        return ResponseEntity.ok(artistManagementService.deleteArtist(artistId));
    }
}