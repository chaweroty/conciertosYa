package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.OurPlacesDTO;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.repository.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceManagementService {

    @Autowired
    private PlaceRepo placeRepo;

    public OurPlacesDTO addPlace(OurPlacesDTO place) {
        OurPlacesDTO response = new OurPlacesDTO();
        try {
            OurPlaces savedPlace = new OurPlaces();
            savePlace(savedPlace, place.getName(), place.getCapacityGeneral(), place.getCapacityVip(), place.getCapacityPalco(), place.getState(), place.getCity(), place.getDirection(), place.getImage());

            OurPlaces ourPlaceResult = placeRepo.save(savedPlace);

            if (ourPlaceResult.getId() > 0) {
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