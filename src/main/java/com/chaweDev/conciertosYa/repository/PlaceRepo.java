package com.chaweDev.conciertosYa.repository;

import com.chaweDev.conciertosYa.entity.OurPlaces;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepo extends JpaRepository<OurPlaces, Integer> {

}
