package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurPlacesDTO;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.service.PlaceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Principio aplicado: Controladores RESTful
// Este controlador implementa operaciones RESTful relacionadas con la gestión de lugares.
// La anotación @RestController y @RequestMapping proporciona una estructura clara para las rutas,
// asegurando consistencia y facilidad de uso para los consumidores del API.
@RestController
@RequestMapping("/places")
public class PlaceManagementController {

    // Principio aplicado: Inversión de Dependencias (DIP)
    // La inyección de dependencias utiliza la interfaz IPlaceManagementService, lo que permite
    // cambiar o extender la implementación sin afectar el controlador.
    @Autowired
    private PlaceManagementService placeManagementService;

    // Principio aplicado: Separación de Responsabilidades (SRP)
    // Este metodo se encarga exclusivamente de delegar la lógica para agregar un nuevo lugar.
    @PostMapping("/add")
    public ResponseEntity<OurPlacesDTO> addPlace(@RequestBody OurPlacesDTO place) {
        OurPlacesDTO response = placeManagementService.addPlace(place);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: Cohesión Alta
    // Este metodo delega la obtención de todos los lugares al servicio, asegurando claridad y separación de responsabilidades.
    @GetMapping("/get-all")
    public ResponseEntity<OurPlacesDTO> getAllPlaces() {
        OurPlacesDTO response = placeManagementService.getAllPlaces();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: Abierto/Cerrado (OCP)
    // Este metodo extiende la funcionalidad al permitir obtener un lugar específico por su ID,
    // manteniendo su estructura básica intacta.
    @GetMapping("/get/{placeId}")
    public ResponseEntity<OurPlacesDTO> getPlaceById(@PathVariable Integer placeId) {
        return ResponseEntity.ok(placeManagementService.getPlaceById(placeId));
    }

    // Principio aplicado: Reutilización de Código
    // La lógica para actualizar un lugar se encapsula en el servicio,
    // manteniendo el controlador limpio y enfocado en la delegación.
    @PutMapping("/update/{placeId}")
    public ResponseEntity<OurPlacesDTO> updatePlace(@PathVariable Integer placeId, @RequestBody OurPlaces place) {
        return ResponseEntity.ok(placeManagementService.updatePlace(placeId, place));
    }

    // Metodo para eliminar un lugar por su ID.
    // Principio aplicado: Reutilización de Código
    // Utiliza el servicio para eliminar, promoviendo una lógica centralizada y consistente.
    @DeleteMapping("/delete/{placeId}")
    public ResponseEntity<OurPlacesDTO> deletePlace(@PathVariable Integer placeId) {
        return ResponseEntity.ok(placeManagementService.deletePlace(placeId));
    }
}