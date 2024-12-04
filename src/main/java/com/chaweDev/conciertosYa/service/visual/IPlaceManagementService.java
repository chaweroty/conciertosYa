package com.chaweDev.conciertosYa.service.Visual;

import com.chaweDev.conciertosYa.dto.OurPlacesDTO;
import com.chaweDev.conciertosYa.entity.OurPlaces;

public interface IPlaceManagementService {
    OurPlacesDTO addPlace(OurPlacesDTO place);

    OurPlacesDTO getAllPlaces();

    OurPlacesDTO getPlaceById(Integer placeId);

    OurPlacesDTO updatePlace(Integer placeId, OurPlaces place);

    OurPlacesDTO deletePlace(Integer placeId);
}
