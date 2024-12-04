package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.OurSeatsDTO;
import com.chaweDev.conciertosYa.entity.OurSeats;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.repository.PlaceRepo;
import com.chaweDev.conciertosYa.repository.SeatRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SeatManagementServiceTest {

    @Mock
    private SeatRepo seatRepo;

    @Mock
    private PlaceRepo placeRepo;

    @InjectMocks
    private SeatManagementService seatManagementService;

    private OurSeatsDTO seatDTO;
    private OurSeats seat;
    private OurPlaces place;

    @BeforeEach
    void setUp() {
        // Setup a mock seat and place
        seatDTO = new OurSeatsDTO();
        seatDTO.setCode("A1");
        seatDTO.setRow(1);
        seatDTO.setColumn(1);
        seatDTO.setPrice(100.0);
        seatDTO.setDiscount(10.0);
        seatDTO.setType("VIP");
        seatDTO.setState("Available");
        seatDTO.setPlace(1); // Explicitly set place ID

        place = new OurPlaces();
        place.setId(1);

        seat = new OurSeats();
        seat.setId(1);
        seat.setCode("A1");
        seat.setRow(1);
        seat.setColumn(1);
        seat.setPrice(100.0);
        seat.setDiscount(10.0);
        seat.setType("VIP");
        seat.setState("Available");
        seat.setPlace(place);
    }

    @Test
    void addSeat_ShouldReturnSuccess_WhenSeatIsSavedSuccessfully() {
        // Arrange
        when(placeRepo.findById(1)).thenReturn(Optional.of(place));
        when(seatRepo.save(any(OurSeats.class))).thenReturn(seat);

        // Act
        OurSeatsDTO response = seatManagementService.addSeat(seatDTO);

        // Assert
        assertEquals(200, response.getStatusCode());
        assertEquals("Seat Saved Successfully", response.getMessage());
    }

    @Test
    void addSeat_ShouldReturnError_WhenSeatSaveFails() {
        // Arrange
        when(placeRepo.findById(1)).thenReturn(Optional.of(place));
        when(seatRepo.save(any(OurSeats.class))).thenReturn(null);

        // Act
        OurSeatsDTO response = seatManagementService.addSeat(seatDTO);

        // Assert
        assertEquals(500, response.getStatusCode());
        assertEquals("Error occurred: ", response.getMessage());
    }

    @Test
    void getAllSeats_ShouldReturnSeats_WhenSeatsExist() {
        // Arrange
        when(seatRepo.findAll()).thenReturn(List.of(seat));

        // Act
        OurSeatsDTO response = seatManagementService.getAllSeats();

        // Assert
        assertEquals(200, response.getStatusCode());
        assertEquals(1, response.getOurSeatsList().size());
        assertEquals("A1", response.getOurSeatsList().get(0).getCode());
    }

    @Test
    void getAllSeats_ShouldReturnNotFound_WhenNoSeatsExist() {
        // Arrange
        when(seatRepo.findAll()).thenReturn(new ArrayList<>());

        // Act
        OurSeatsDTO response = seatManagementService.getAllSeats();

        // Assert
        assertEquals(404, response.getStatusCode());
        assertEquals("No seats found", response.getMessage());
    }

    @Test
    void updateSeat_ShouldReturnUpdatedSeat_WhenSeatIsFoundAndUpdated() {
        // Arrange
        OurSeatsDTO updatedSeatDTO = new OurSeatsDTO();
        updatedSeatDTO.setCode("B1");
        updatedSeatDTO.setRow(2);
        updatedSeatDTO.setColumn(2);
        updatedSeatDTO.setPrice(150.0);
        updatedSeatDTO.setDiscount(20.0);
        updatedSeatDTO.setType("Regular");
        updatedSeatDTO.setState("Booked");
        updatedSeatDTO.setPlace(1); // Set place ID

        // Prepare the existing seat
        when(seatRepo.findById(1)).thenReturn(Optional.of(seat));
        when(placeRepo.findById(1)).thenReturn(Optional.of(place));

        // Prepare the updated seat to be returned by save
        OurSeats updatedSeat = new OurSeats();
        updatedSeat.setId(1);
        updatedSeat.setCode("B1");
        updatedSeat.setRow(2);
        updatedSeat.setColumn(2);
        updatedSeat.setPrice(150.0);
        updatedSeat.setDiscount(20.0);
        updatedSeat.setType("Regular");
        updatedSeat.setState("Booked");
        updatedSeat.setPlace(place);

        when(seatRepo.save(any(OurSeats.class))).thenReturn(updatedSeat);

        // Act
        OurSeatsDTO response = seatManagementService.updateSeat(1, updatedSeatDTO);

        // Assert
        assertEquals(200, response.getStatusCode(), "Status code should be 200 for successful update");
        assertEquals("Seat updated successfully", response.getMessage());
    }

    @Test
    void deleteSeat_ShouldReturnSuccess_WhenSeatIsDeleted() {
        // Arrange
        when(seatRepo.findById(anyInt())).thenReturn(Optional.of(seat));

        // Act
        OurSeatsDTO response = seatManagementService.deleteSeat(1);

        // Assert
        assertEquals(200, response.getStatusCode());
        assertEquals("Seat deleted successfully", response.getMessage());
    }

    @Test
    void deleteSeat_ShouldReturnNotFound_WhenSeatDoesNotExist() {
        // Arrange
        when(seatRepo.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        OurSeatsDTO response = seatManagementService.deleteSeat(1);

        // Assert
        assertEquals(404, response.getStatusCode());
        assertEquals("Seat not found", response.getMessage());
    }
}