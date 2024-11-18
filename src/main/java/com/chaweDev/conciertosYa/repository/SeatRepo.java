package com.chaweDev.conciertosYa.repository;

import com.chaweDev.conciertosYa.entity.OurSeats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepo extends JpaRepository<OurSeats, Integer> {

}
