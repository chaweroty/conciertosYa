package com.chaweDev.conciertosYa.service.Visual;

import com.chaweDev.conciertosYa.dto.OurArtistsDTO;
import com.chaweDev.conciertosYa.entity.OurArtists;

public interface IArtistManagementService {
    OurArtistsDTO addArtist(OurArtistsDTO artist);

    OurArtistsDTO getAllArtists();

    OurArtistsDTO getArtistById(Integer artistId);

    OurArtistsDTO updateArtist(Integer artistId, OurArtists artist);

    OurArtistsDTO deleteArtist(Integer artistId);
}
