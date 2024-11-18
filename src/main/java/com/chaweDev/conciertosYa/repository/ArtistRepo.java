package com.chaweDev.conciertosYa.repository;

import com.chaweDev.conciertosYa.entity.OurArtists;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepo extends JpaRepository<OurArtists, Integer> {

}