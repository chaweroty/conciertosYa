package com.chaweDev.conciertosYa.service.visual;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurArtistsDTO;
import com.chaweDev.conciertosYa.entity.OurArtists;

public interface IArtistManagementService {
    OurArtistsDTO addArtist(DTO artist);

    OurArtistsDTO getAllArtists();

    OurArtistsDTO getArtistById(Integer artistId);

    OurArtistsDTO updateArtist(Integer artistId, OurArtists artist);

    OurArtistsDTO deleteArtist(Integer artistId);
}
