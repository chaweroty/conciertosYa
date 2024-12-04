package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurEventsDTO;
import com.chaweDev.conciertosYa.dto.OurPlacesDTO;
import com.chaweDev.conciertosYa.entity.OurEvents;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.repository.PlaceRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PlaceManagementServiceTest {

    @InjectMocks
    private PlaceManagementService placeManagementService;

    @Mock
    private PlaceRepo placeRepo;

    private OurPlacesDTO placeDTO;
    private OurPlaces placeEntity;

    @BeforeEach
    void setUp() {
        placeDTO = new OurPlacesDTO();

        placeDTO.setName("Test Place");
        placeDTO.setCapacityGeneral(100);
        placeDTO.setPriceGen(100.00);
        placeDTO.setDiscountGen(10.00);
        placeDTO.setCapacityVip(50);
        placeDTO.setPriceVip(100.00);
        placeDTO.setDiscountVip(10.00);
        placeDTO.setCapacityPalco(30);
        placeDTO.setPricePalco(100.00);
        placeDTO.setDiscountPalco(10.00);
        placeDTO.setState("Active");
        placeDTO.setCity("Test City");
        placeDTO.setDirection("Test Direction");
        placeDTO.setImage("Test Image");

        placeEntity = new OurPlaces();
        placeEntity.setId(1);
        placeEntity.setName("Test Place");
        placeEntity.setCapacityGeneral(100);
        placeEntity.setCapacityVip(50);
        placeEntity.setCapacityPalco(30);
        placeEntity.setState("Active");
        placeEntity.setCity("Test City");
        placeEntity.setDirection("Test Direction");
        placeEntity.setImage("Test Image");
    }

    @Test
    void testAddPlace_Failure_No_seatService() {
        // Given
        when(placeRepo.save(any(OurPlaces.class))).thenReturn(placeEntity);

        // When
        OurPlacesDTO response = placeManagementService.addPlace(placeDTO);
        // Then
        assertEquals(500, response.getStatusCode());
    }

    @Test
    void testAddPlace_Failure_InvalidDTO() {
        // Given
        OurEventsDTO invalidDTO = new OurEventsDTO();

        // When
        OurPlacesDTO response = placeManagementService.addPlace(invalidDTO);

        // Then
        assertEquals(400, response.getStatusCode());
        assertEquals("Invalid DTO type", response.getMessage());
    }

    @Test
    void testGetAllPlaces_Success() {
        // Given
        when(placeRepo.findAll()).thenReturn(List.of(placeEntity));

        // When
        OurPlacesDTO response = placeManagementService.getAllPlaces();

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("Places found successfully", response.getMessage());
        assertNotNull(response.getOurPlacesList());
        assertEquals(1, response.getOurPlacesList().size());
    }

    @Test
    void testGetPlaceById_Success() {
        // Given
        when(placeRepo.findById(1)).thenReturn(Optional.of(placeEntity));

        // When
        OurPlacesDTO response = placeManagementService.getPlaceById(1);

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("Place found successfully", response.getMessage());
        assertEquals(placeEntity, response.getOurPlaces());
    }

    @Test
    void testGetPlaceById_Failure_NotFound() {
        // Given
        when(placeRepo.findById(1)).thenReturn(Optional.empty());

        // When
        OurPlacesDTO response = placeManagementService.getPlaceById(1);

        // Then
        assertEquals(500, response.getStatusCode());
        assertEquals("Error occurred: Place not found", response.getMessage());
    }

    @Test
    void testUpdatePlace_Success() {
        // Given
        OurPlaces updatedPlace = new OurPlaces();
        updatedPlace.setName("Updated Place");
        updatedPlace.setCapacityGeneral(200);
        when(placeRepo.findById(1)).thenReturn(Optional.of(placeEntity));
        when(placeRepo.save(any(OurPlaces.class))).thenReturn(updatedPlace);

        // When
        OurPlacesDTO response = placeManagementService.updatePlace(1, updatedPlace);

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("Place updated successfully", response.getMessage());
        assertEquals(updatedPlace, response.getOurPlaces());
    }

    @Test
    void testUpdatePlace_Failure_NotFound() {
        // Given
        OurPlaces updatedPlace = new OurPlaces();
        updatedPlace.setName("Updated Place");
        updatedPlace.setCapacityGeneral(200);
        when(placeRepo.findById(1)).thenReturn(Optional.empty());

        // When
        OurPlacesDTO response = placeManagementService.updatePlace(1, updatedPlace);

        // Then
        assertEquals(404, response.getStatusCode());
        assertEquals("Place not found", response.getMessage());
    }

    @Test
    void testDeletePlace_Success() {
        // Given
        when(placeRepo.findById(1)).thenReturn(Optional.of(placeEntity));

        // When
        OurPlacesDTO response = placeManagementService.deletePlace(1);

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("Place deleted successfully", response.getMessage());
    }

    @Test
    void testDeletePlace_Failure_NotFound() {
        // Given
        when(placeRepo.findById(1)).thenReturn(Optional.empty());

        // When
        OurPlacesDTO response = placeManagementService.deletePlace(1);

        // Then
        assertEquals(404, response.getStatusCode());
        assertEquals("Place not found", response.getMessage());
    }
}