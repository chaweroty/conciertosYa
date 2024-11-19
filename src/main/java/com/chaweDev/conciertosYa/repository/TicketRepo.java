package com.chaweDev.conciertosYa.repository;

import com.chaweDev.conciertosYa.entity.OurTickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<OurTickets, Integer> {

}
