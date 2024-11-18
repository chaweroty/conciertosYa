package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.OurArtistsDTO;
import com.chaweDev.conciertosYa.entity.OurArtists;
import com.chaweDev.conciertosYa.repository.ArtistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistManagementService {

    @Autowired
    private ArtistRepo artistRepo;

    public OurArtistsDTO addArtist(OurArtistsDTO artist) {
        OurArtistsDTO response = new OurArtistsDTO();
        try {

            OurArtists savedArtist = new OurArtists();

            savedArtist.setName(artist.getName());
            savedArtist.setMusicalGenre(artist.getMusicalGenre());
            savedArtist.setInstagram(artist.getInstagram());
            savedArtist.setFacebook(artist.getFacebook());
            savedArtist.setContact(artist.getContact());

            OurArtists ourArtistResult = artistRepo.save(savedArtist);
            if (ourArtistResult.getId() > 0) {
                response.setOurArtists(ourArtistResult);
                response.setMessage("User Saved Successfully");
                response.setStatusCode(200);
            } else {
                response.setMessage("User not saved due to an unknown error.");
                response.setStatusCode(500);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurArtistsDTO getAllArtists() {
        OurArtistsDTO response = new OurArtistsDTO();
        try {
            List<OurArtists> artists = artistRepo.findAll();
            if (artists.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No artists found");
            } else {
                response.setStatusCode(200);
                response.setOurArtistsList(artists);
                response.setMessage("Artists found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurArtistsDTO getArtistById(Integer artistId) {
        OurArtistsDTO response = new OurArtistsDTO();
        try {
            OurArtists artist = artistRepo.findById(artistId).orElseThrow(() -> new RuntimeException("Artist not found"));
            response.setStatusCode(200);
            response.setOurArtists(artist);
            response.setMessage("Artist found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurArtistsDTO updateArtist(Integer artistId, OurArtists artist) {
        OurArtistsDTO response = new OurArtistsDTO();
        try {
            Optional<OurArtists> existingArtistOpt = artistRepo.findById(artistId);
            if (existingArtistOpt.isPresent()) {
                OurArtists existingArtist = existingArtistOpt.get();

                existingArtist.setName(artist.getName());
                existingArtist.setMusicalGenre(artist.getMusicalGenre());
                existingArtist.setInstagram(artist.getInstagram());
                existingArtist.setFacebook(artist.getFacebook());
                existingArtist.setContact(artist.getContact());

                OurArtists updatedArtist = artistRepo.save(existingArtist);

                response.setStatusCode(200);
                response.setOurArtists(updatedArtist);
                response.setMessage("Artist updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Artist not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurArtistsDTO deleteArtist(Integer artistId) {
        OurArtistsDTO response = new OurArtistsDTO();
        try {
            Optional<OurArtists> artistOptional = artistRepo.findById(artistId);
            if (artistOptional.isPresent()) {
                artistRepo.deleteById(artistId);
                response.setStatusCode(200);
                response.setMessage("Artist deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Artist not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting artist: " + e.getMessage());
        }
        return response;
    }
}