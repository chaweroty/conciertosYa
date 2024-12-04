package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.OurArtistsDTO;
import com.chaweDev.conciertosYa.entity.OurArtists;
import com.chaweDev.conciertosYa.repository.ArtistRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ArtistManagementServiceTest {

    @InjectMocks
    private ArtistManagementService artistManagementService;

    @Mock
    private ArtistRepo artistRepo;

    private OurArtists testArtist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testArtist = new OurArtists();
        testArtist.setId(1);
        testArtist.setName("Test Artist");
        testArtist.setMusicalGenre("Pop");
        testArtist.setInstagram("test_instagram");
        testArtist.setFacebook("test_facebook");
        testArtist.setContact("123-456-789");
    }

    @Test
    void testAddArtistSuccess() {
        // Given
        OurArtistsDTO artistDTO = new OurArtistsDTO.Builder()
                .name(testArtist.getName())
                .musicalGenre(testArtist.getMusicalGenre())
                .instagram(testArtist.getInstagram())
                .facebook(testArtist.getFacebook())
                .contact(testArtist.getContact())
                .build();

        when(artistRepo.save(any(OurArtists.class))).thenReturn(testArtist);

        // When
        OurArtistsDTO response = artistManagementService.addArtist(artistDTO);

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("Artist Saved Successfully", response.getMessage());
    }

    @Test
    void testAddArtistFailure() {
        // Given
        OurArtistsDTO artistDTO = new OurArtistsDTO.Builder()
                .name(testArtist.getName())
                .musicalGenre(testArtist.getMusicalGenre())
                .instagram(testArtist.getInstagram())
                .facebook(testArtist.getFacebook())
                .contact(testArtist.getContact())
                .build();

        when(artistRepo.save(any(OurArtists.class))).thenReturn(null);

        // When
        OurArtistsDTO response = artistManagementService.addArtist(artistDTO);

        // Then
        assertEquals(500, response.getStatusCode());
        assertEquals("Error occurred: ", response.getMessage());
    }

    @Test
    void testGetAllArtistsFound() {
        // Given
        when(artistRepo.findAll()).thenReturn(List.of(testArtist));

        // When
        OurArtistsDTO response = artistManagementService.getAllArtists();

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("Artists found successfully", response.getMessage());
        assertNotNull(response.getOurArtistsList());
    }

    @Test
    void testGetAllArtistsNotFound() {
        // Given
        when(artistRepo.findAll()).thenReturn(List.of());

        // When
        OurArtistsDTO response = artistManagementService.getAllArtists();

        // Then
        assertEquals(404, response.getStatusCode());
        assertEquals("No artists found", response.getMessage());
    }

    @Test
    void testGetArtistByIdSuccess() {
        // Given
        when(artistRepo.findById(testArtist.getId())).thenReturn(Optional.of(testArtist));

        // When
        OurArtistsDTO response = artistManagementService.getArtistById(testArtist.getId());

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("Artist found successfully", response.getMessage());
        assertEquals(testArtist.getName(), response.getOurArtists().getName());
    }

    @Test
    void testGetArtistByIdNotFound() {
        // Given
        when(artistRepo.findById(testArtist.getId())).thenReturn(Optional.empty());

        // When
        OurArtistsDTO response = artistManagementService.getArtistById(testArtist.getId());

        // Then
        assertEquals(500, response.getStatusCode());
        assertEquals("Error occurred: Artist not found", response.getMessage());
    }

    @Test
    void testUpdateArtistSuccess() {
        // Given
        OurArtists updatedArtist = new OurArtists();
        updatedArtist.setId(testArtist.getId());
        updatedArtist.setName("Updated Artist");

        when(artistRepo.findById(testArtist.getId())).thenReturn(Optional.of(testArtist));
        when(artistRepo.save(any(OurArtists.class))).thenReturn(updatedArtist);

        // When
        OurArtistsDTO response = artistManagementService.updateArtist(testArtist.getId(), updatedArtist);

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("Artist updated successfully", response.getMessage());
    }

    @Test
    void testUpdateArtistNotFound() {
        // Given
        when(artistRepo.findById(testArtist.getId())).thenReturn(Optional.empty());

        // When
        OurArtistsDTO response = artistManagementService.updateArtist(testArtist.getId(), testArtist);

        // Then
        assertEquals(404, response.getStatusCode());
        assertEquals("No artists found", response.getMessage());
    }

    @Test
    void testDeleteArtistSuccess() {
        // Given
        when(artistRepo.findById(testArtist.getId())).thenReturn(Optional.of(testArtist));

        // When
        OurArtistsDTO response = artistManagementService.deleteArtist(testArtist.getId());

        // Then
        assertEquals(200, response.getStatusCode());
        assertEquals("Artist deleted successfully", response.getMessage());
    }

    @Test
    void testDeleteArtistNotFound() {
        // Given
        when(artistRepo.findById(testArtist.getId())).thenReturn(Optional.empty());

        // When
        OurArtistsDTO response = artistManagementService.deleteArtist(testArtist.getId());

        // Then
        assertEquals(404, response.getStatusCode());
        assertEquals("No artists found", response.getMessage());
    }
}
