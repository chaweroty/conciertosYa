package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurArtistsDTO;
import com.chaweDev.conciertosYa.entity.OurArtists;
import com.chaweDev.conciertosYa.repository.ArtistRepo;
import com.chaweDev.conciertosYa.service.visual.IArtistManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistManagementService implements IArtistManagementService {

    @Autowired
    private ArtistRepo artistRepo;

    /*
    Instanciación de OurArtistsDTO desde un objeto de tipo DTO:
    El metodo addArtist recibe un parámetro de tipo DTO, que podría ser cualquier objeto que implemente la interfaz DTO.
    La expresión instanceof OurArtistsDTO verifica si el objeto dto es una instancia de OurArtistsDTO,
    lo que permite que se pueda tratar específicamente como un objeto de tipo OurArtistsDTO. Si el objeto es de ese tipo,
    el código dentro del bloque if se ejecuta.

    Asignación a una variable del tipo OurArtistsDTO:
    El uso de instanceof no solo verifica el tipo del objeto,
    sino que también realiza un casting implícito del objeto dto a OurArtistsDTO.
    Esto significa que el objeto dto es tratado como un OurArtistsDTO dentro del bloque if.
    Esta técnica permite que se puedan acceder de manera directa a los métodos y propiedades específicos de OurArtistsDTO sin necesidad de hacer un cast manual,
    y es posible gracias al polimorfismo.
    * */
    public OurArtistsDTO addArtist(DTO dto) {
        OurArtistsDTO response;
        try {
            if (dto instanceof OurArtistsDTO artist) {

                // Crear la entidad OurArtists utilizando su constructor o Builder
                OurArtists savedArtist = new OurArtists();
                savedArtist.setName(artist.getName());
                savedArtist.setMusicalGenre(artist.getMusicalGenre());
                savedArtist.setInstagram(artist.getInstagram());
                savedArtist.setFacebook(artist.getFacebook());
                savedArtist.setContact(artist.getContact());

                // Guardar la entidad
                OurArtists ourArtistResult = artistRepo.save(savedArtist);

                // Construir el DTO de respuesta
                if (ourArtistResult.getId() > 0) {
                    response = new OurArtistsDTO.Builder()
                            .ourArtists(ourArtistResult)
                            .message("Artist Saved Successfully")
                            .statusCode(200)
                            .build();
                } else {
                    response = new OurArtistsDTO.Builder()
                            .message("Artist not saved due to an unknown error.")
                            .statusCode(500)
                            .build();
                }

            } else {
                response = new OurArtistsDTO.Builder()
                        .message("Invalid DTO type")
                        .statusCode(400)
                        .build();
            }
        } catch (Exception e) {
            response = new OurArtistsDTO.Builder()
                    .message("Error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();

        }
        return response;
    }

    public OurArtistsDTO getAllArtists() {
        OurArtistsDTO response;
        try {
            List<OurArtists> artists = artistRepo.findAll();
            if (artists.isEmpty()) {
                response = new OurArtistsDTO.Builder()
                        .message("No artists found")
                        .statusCode(404)
                        .build();
            } else {
                response = new OurArtistsDTO.Builder()
                        .ourArtistsList(artists)
                        .message("Artists found successfully")
                        .statusCode(200)
                        .build();
            }
        } catch (Exception e) {
            response = new OurArtistsDTO.Builder()
                    .message("Error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
        return response;
    }

    public OurArtistsDTO getArtistById(Integer artistId) {
        OurArtistsDTO response;
        try {
            OurArtists artist = artistRepo.findById(artistId).orElseThrow(() -> new RuntimeException("Artist not found"));
            response = new OurArtistsDTO.Builder()
                    .ourArtists(artist)
                    .message("Artist found successfully")
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            response = new OurArtistsDTO.Builder()
                    .message("Error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
        return response;
    }

    public OurArtistsDTO updateArtist(Integer artistId, OurArtists artist) {
        OurArtistsDTO response;
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

                response = new OurArtistsDTO.Builder()
                        .ourArtists(updatedArtist)
                        .message("Artist updated successfully")
                        .statusCode(200)
                        .build();
            } else {
                response = new OurArtistsDTO.Builder()
                        .message("No artists found")
                        .statusCode(404)
                        .build();
            }
        } catch (Exception e) {
            response = new OurArtistsDTO.Builder()
                    .message("Error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
        return response;
    }

    public OurArtistsDTO deleteArtist(Integer artistId) {
        OurArtistsDTO response;
        try {
            Optional<OurArtists> artistOptional = artistRepo.findById(artistId);
            if (artistOptional.isPresent()) {
                artistRepo.deleteById(artistId);
                response = new OurArtistsDTO.Builder()
                        .message("Artist deleted successfully")
                        .statusCode(200)
                        .build();
            } else {
                response = new OurArtistsDTO.Builder()
                        .message("No artists found")
                        .statusCode(404)
                        .build();
            }
        } catch (Exception e) {
            response = new OurArtistsDTO.Builder()
                    .message("Error occurred while deleting artist: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
        return response;
    }
}