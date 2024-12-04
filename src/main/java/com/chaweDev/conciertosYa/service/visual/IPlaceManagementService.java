package com.chaweDev.conciertosYa.service.visual;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurPlacesDTO;
import com.chaweDev.conciertosYa.entity.OurPlaces;

public interface IPlaceManagementService {
    OurPlacesDTO addPlace(DTO place);

    OurPlacesDTO getAllPlaces();

    OurPlacesDTO getPlaceById(Integer placeId);

    OurPlacesDTO updatePlace(Integer placeId, OurPlaces place);

    OurPlacesDTO deletePlace(Integer placeId);
}
