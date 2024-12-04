package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurArtists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OurArtistsDTOTest {

    @Test
    void testDefaultConstructor() {
        OurArtistsDTO dto = new OurArtistsDTO();
        assertNotNull(dto);
    }

    @Test
    void testFullConstructor() {
        OurArtists artist = new OurArtists(); // You might need to mock this or create a test instance
        List<OurArtists> artistsList = new ArrayList<>();
        artistsList.add(artist);

        OurArtistsDTO dto = new OurArtistsDTO(
                200,
                null,
                "Success",
                "Test Artist",
                "Rock",
                "@testartist",
                "facebook.com/testartist",
                "contact@test.com",
                artist,
                artistsList
        );

        assertEquals(200, dto.getStatusCode());
        assertEquals("Success", dto.getMessage());
        assertEquals("Test Artist", dto.getName());
        assertEquals("Rock", dto.getMusicalGenre());
        assertEquals("@testartist", dto.getInstagram());
        assertEquals("facebook.com/testartist", dto.getFacebook());
        assertEquals("contact@test.com", dto.getContact());
        assertNotNull(dto.getOurArtists());
        assertNotNull(dto.getOurArtistsList());
    }

    @Test
    void testBuilderPattern() {
        OurArtists artist = new OurArtists(); // You might need to mock this or create a test instance
        List<OurArtists> artistsList = new ArrayList<>();
        artistsList.add(artist);

        OurArtistsDTO dto = new OurArtistsDTO.Builder()
                .statusCode(200)
                .message("Success")
                .name("Test Artist")
                .musicalGenre("Rock")
                .instagram("@testartist")
                .facebook("facebook.com/testartist")
                .contact("contact@test.com")
                .ourArtists(artist)
                .ourArtistsList(artistsList)
                .build();

        assertEquals(200, dto.getStatusCode());
        assertEquals("Success", dto.getMessage());
        assertEquals("Test Artist", dto.getName());
        assertEquals("Rock", dto.getMusicalGenre());
        assertEquals("@testartist", dto.getInstagram());
        assertEquals("facebook.com/testartist", dto.getFacebook());
        assertEquals("contact@test.com", dto.getContact());
        assertNotNull(dto.getOurArtists());
        assertNotNull(dto.getOurArtistsList());
    }

    @Test
    void testSettersAndGetters() {
        OurArtistsDTO dto = new OurArtistsDTO();

        dto.setName("Updated Artist");
        assertEquals("Updated Artist", dto.getName());

        dto.setMusicalGenre("Jazz");
        assertEquals("Jazz", dto.getMusicalGenre());

        dto.setInstagram("@updatedartist");
        assertEquals("@updatedartist", dto.getInstagram());

        dto.setFacebook("facebook.com/updatedartist");
        assertEquals("facebook.com/updatedartist", dto.getFacebook());

        dto.setContact("newcontact@test.com");
        assertEquals("newcontact@test.com", dto.getContact());
    }

    @Test
    void testNullProperties() {
        OurArtistsDTO dto = new OurArtistsDTO();

        assertNull(dto.getName());
        assertNull(dto.getMusicalGenre());
        assertNull(dto.getInstagram());
        assertNull(dto.getFacebook());
        assertNull(dto.getContact());
        assertNull(dto.getOurArtists());
        assertNull(dto.getOurArtistsList());
    }

    @Test
    void testBuilderWithPartialProperties() {
        OurArtistsDTO dto = new OurArtistsDTO.Builder()
                .name("Partial Artist")
                .musicalGenre("Pop")
                .build();

        assertEquals("Partial Artist", dto.getName());
        assertEquals("Pop", dto.getMusicalGenre());
        assertNull(dto.getInstagram());
        assertNull(dto.getFacebook());
        assertNull(dto.getContact());
    }
}