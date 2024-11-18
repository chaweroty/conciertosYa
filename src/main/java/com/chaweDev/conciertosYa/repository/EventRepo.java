package com.chaweDev.conciertosYa.repository;

import com.chaweDev.conciertosYa.entity.OurEvents;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<OurEvents, Integer> {

}
