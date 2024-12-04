package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurArtistsDTO;
import com.chaweDev.conciertosYa.entity.OurArtists;
import com.chaweDev.conciertosYa.service.ArtistManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Principio aplicado: Controladores RESTful
// Este controlador sigue el patrón REST para manejar solicitudes HTTP relacionadas con artistas.
// El uso de @RestController y @RequestMapping organiza las rutas de forma clara y consistente,
// promoviendo buenas prácticas de diseño y simplicidad en el uso del API.
@RestController
@RequestMapping("/artists")
public class ArtistManagementController {

    // Principio aplicado: Inversión de Dependencias (DIP)
    // Se inyecta una interfaz (IArtistManagementService) para evitar acoplamiento directo
    // con implementaciones concretas, promoviendo flexibilidad y mejorando la testabilidad.
    @Autowired
    private ArtistManagementService artistManagementService;

    // Principio aplicado: Separación de Responsabilidades (SRP)
    // Este metodo maneja exclusivamente la creación de artistas, delegando toda la lógica
    // de negocio al servicio correspondiente.
    @PostMapping("/add")
    public ResponseEntity<OurArtistsDTO> addArtist(@RequestBody OurArtistsDTO artist) {
        OurArtistsDTO response = artistManagementService.addArtist(artist);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: DRY (Don't Repeat Yourself)
    // Este metodo utiliza el servicio para obtener todos los artistas sin duplicar lógica de negocio,
    // promoviendo la reutilización del código.
    @GetMapping("/get-all")
    public ResponseEntity<OurArtistsDTO> getAllArtists() {
        OurArtistsDTO response = artistManagementService.getAllArtists();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: Abierto/Cerrado (OCP)
    // Este metodo es extensible para manejar diferentes consultas por ID, siempre y cuando
    // el servicio gestione adecuadamente la lógica interna, sin necesidad de modificar el controlador.
    @GetMapping("/get/{artistId}")
    public ResponseEntity<OurArtistsDTO> getArtistById(@PathVariable Integer artistId) {
        return ResponseEntity.ok(artistManagementService.getArtistById(artistId));
    }

    // Principio aplicado: Cohesión Alta
    // Este metodo se enfoca en actualizar un artista específico, delegando la lógica de
    // actualización al servicio y manteniendo una alta cohesión en el controlador.
    @PutMapping("/update/{artistId}")
    public ResponseEntity<OurArtistsDTO> updateArtist(@PathVariable Integer artistId, @RequestBody OurArtists artist) {
        return ResponseEntity.ok(artistManagementService.updateArtist(artistId, artist));
    }

    // Principio aplicado: Bajo Acoplamiento
    // Este metodo se ocupa exclusivamente de eliminar artistas, asegurando que el controlador
    // no dependa de la lógica interna y esta se gestione en el servicio.
    @DeleteMapping("/delete/{artistId}")
    public ResponseEntity<OurArtistsDTO> deleteArtist(@PathVariable Integer artistId) {
        return ResponseEntity.ok(artistManagementService.deleteArtist(artistId));
    }
}