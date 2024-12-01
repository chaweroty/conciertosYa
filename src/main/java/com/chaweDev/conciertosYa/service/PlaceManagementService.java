package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.OurPlacesDTO;
import com.chaweDev.conciertosYa.dto.OurSeatsDTO;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.repository.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class
PlaceManagementService {

    @Autowired
    private PlaceRepo placeRepo;
    @Autowired
    private SeatManagementService seatService;

    public OurPlacesDTO addPlace(OurPlacesDTO place) {
        OurPlacesDTO response = new OurPlacesDTO();
        try {
            OurPlaces savedPlace = new OurPlaces();
            savePlace(savedPlace, place.getName(), place.getCapacityGeneral(), place.getCapacityVip(),
                    place.getCapacityPalco(), place.getState(), place.getCity(),
                    place.getDirection(), place.getImage());

            OurPlaces ourPlaceResult = placeRepo.save(savedPlace);

            if (ourPlaceResult.getId() > 0) {
                // Crear asientos según las capacidades indicadas
                createSeatsForPlace(ourPlaceResult, place.getCapacityGeneral(),
                        place.getCapacityVip(), place.getCapacityPalco());

                response.setOurPlaces(ourPlaceResult);
                response.setMessage("Place Saved Successfully");
                response.setStatusCode(200);
            } else {
                response.setMessage("Place not saved due to an unknown error.");
                response.setStatusCode(500);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    private void createSeatsForPlace(OurPlaces place, int generalCapacity, int vipCapacity, int palcoCapacity) {
        // Crear asientos generales
        createSeatsByType(place, generalCapacity, "General", "GEN", 50.0);

        // Crear asientos VIP
        createSeatsByType(place, vipCapacity, "VIP", "VIP", 100.0);

        // Crear asientos Palco
        createSeatsByType(place, palcoCapacity, "Palco", "PALCO", 150.0);
    }

    private void createSeatsByType(OurPlaces place, int capacity, String type, String codePrefix, double price) {
        int rows = (int) Math.ceil(Math.sqrt(capacity)); // Calcular filas necesarias
        int columns = (int) Math.ceil((double) capacity / rows); // Calcular columnas necesarias

        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                int seatNumber = (row - 1) * columns + column;
                if (seatNumber > capacity) break; // No crear más asientos de los necesarios

                OurSeatsDTO seatDTO = new OurSeatsDTO();
                seatDTO.setCode(codePrefix + "-" + seatNumber); // Código único
                seatDTO.setRow(row); // Número de fila
                seatDTO.setColumn(column); // Número de columna
                seatDTO.setType(type); // Tipo de asiento (General, VIP, Palco)
                seatDTO.setPrice(price); // Precio del asiento
                seatDTO.setState("Available"); // Estado del asiento
                seatDTO.setPlace(place.getId()); // Relacionar con el lugar

                // Llamar al servicio para guardar el asiento
                seatService.addSeat(seatDTO);
            }
        }
    }

    private static void savePlace(OurPlaces savedPlace, String name, Integer capacityGeneral, Integer capacityVip, Integer capacityPalco, String state, String city, String direction, String image) {
        savedPlace.setName(name);
        savedPlace.setCapacityGeneral(capacityGeneral);
        savedPlace.setCapacityVip(capacityVip);
        savedPlace.setCapacityPalco(capacityPalco);
        savedPlace.setState(state);
        savedPlace.setCity(city);
        savedPlace.setDirection(direction);
        savedPlace.setImage(image);
    }

    public OurPlacesDTO getAllPlaces() {
        OurPlacesDTO response = new OurPlacesDTO();
        try {
            List<OurPlaces> places = placeRepo.findAll();
            if (places.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No places found");
            } else {
                response.setStatusCode(200);
                response.setOurPlacesList(places);
                response.setMessage("Places found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurPlacesDTO getPlaceById(Integer placeId) {
        OurPlacesDTO response = new OurPlacesDTO();
        try {
            OurPlaces place = placeRepo.findById(placeId).orElseThrow(() -> new RuntimeException("Place not found"));
            response.setStatusCode(200);
            response.setOurPlaces(place);
            response.setMessage("Place found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurPlacesDTO updatePlace(Integer placeId, OurPlaces place) {
        OurPlacesDTO response = new OurPlacesDTO();
        try {
            Optional<OurPlaces> existingPlaceOpt = placeRepo.findById(placeId);
            if (existingPlaceOpt.isPresent()) {
                OurPlaces existingPlace = getUpdateExistingPlace(place, existingPlaceOpt);

                OurPlaces updatedPlace = placeRepo.save(existingPlace);
                response.setStatusCode(200);
                response.setOurPlaces(updatedPlace);
                response.setMessage("Place updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Place not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    private static OurPlaces getUpdateExistingPlace(OurPlaces place, Optional<OurPlaces> existingPlaceOpt) {
        OurPlaces existingPlace = existingPlaceOpt.get();
        savePlace(existingPlace, place.getName(), place.getCapacityGeneral(), place.getCapacityVip(), place.getCapacityPalco(), place.getState(), place.getCity(), place.getDirection(), place.getImage());
        return existingPlace;
    }

    public OurPlacesDTO deletePlace(Integer placeId) {
        OurPlacesDTO response = new OurPlacesDTO();
        try {
            Optional<OurPlaces> placeOptional = placeRepo.findById(placeId);
            if (placeOptional.isPresent()) {
                placeRepo.deleteById(placeId);
                response.setStatusCode(200);
                response.setMessage("Place deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Place not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting place: " + e.getMessage());
        }
        return response;
    }
}