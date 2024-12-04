package com.chaweDev.conciertosYa.controller;


import com.chaweDev.conciertosYa.dto.OurEventsDTO;
import com.chaweDev.conciertosYa.service.EventManagementService;
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

class EventManagementControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventManagementService eventManagementService;

    @InjectMocks
    private EventManagementController eventManagementController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventManagementController).build();
        objectMapper = new ObjectMapper();
    }




    @Test
    void getEventById() throws Exception {
        OurEventsDTO mockResponse = new OurEventsDTO();
        mockResponse.setName("Jazz Night");
        mockResponse.setStatus("200");

        when(eventManagementService.getEventById(1)).thenReturn(mockResponse);

        mockMvc.perform(get("/events/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jazz Night"));

        verify(eventManagementService, times(1)).getEventById(1);
    }

    @Test
    void deleteEvent() throws Exception {
        OurEventsDTO mockResponse = new OurEventsDTO();
        mockResponse.setStatus("200");

        when(eventManagementService.deleteEvent(1)).thenReturn(mockResponse);

        mockMvc.perform(delete("/events/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));

        verify(eventManagementService, times(1)).deleteEvent(1);
    }
}