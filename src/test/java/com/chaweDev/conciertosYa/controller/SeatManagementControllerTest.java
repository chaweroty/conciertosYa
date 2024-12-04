package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.OurSeatsDTO;
import com.chaweDev.conciertosYa.service.SeatManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class SeatManagementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SeatManagementService seatManagementService;

    @InjectMocks
    private SeatManagementController seatManagementController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(seatManagementController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getSeatById() throws Exception {
        OurSeatsDTO mockResponse = new OurSeatsDTO();
        mockResponse.setCode("A1");
        mockResponse.setState("200");

        when(seatManagementService.getSeatById(1)).thenReturn(mockResponse);

        mockMvc.perform(get("/seats/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("A1"));

        verify(seatManagementService, times(1)).getSeatById(1);
    }

    @Test
    void getSeatPlaceById() throws Exception {
        OurSeatsDTO mockResponse = new OurSeatsDTO();
        mockResponse.setState("200");

        when(seatManagementService.getSeatPlaceById(1)).thenReturn(mockResponse);

        mockMvc.perform(get("/seats/get-place-seats/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("200"));

        verify(seatManagementService, times(1)).getSeatPlaceById(1);
    }

    @Test
    void updateSeat() throws Exception {
        OurSeatsDTO mockResponse = new OurSeatsDTO();
        mockResponse.setCode("A2");
        mockResponse.setState("Updated");

        OurSeatsDTO seatUpdate = new OurSeatsDTO();
        seatUpdate.setCode("A2");

        when(seatManagementService.updateSeat(eq(1), any(OurSeatsDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(put("/seats/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(seatUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("A2"))
                .andExpect(jsonPath("$.state").value("Updated"));

        verify(seatManagementService, times(1)).updateSeat(eq(1), any(OurSeatsDTO.class));
    }

    @Test
    void deleteSeat() throws Exception {
        OurSeatsDTO mockResponse = new OurSeatsDTO();
        mockResponse.setState("Deleted");

        when(seatManagementService.deleteSeat(1)).thenReturn(mockResponse);

        mockMvc.perform(delete("/seats/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("Deleted"));

        verify(seatManagementService, times(1)).deleteSeat(1);
    }
}