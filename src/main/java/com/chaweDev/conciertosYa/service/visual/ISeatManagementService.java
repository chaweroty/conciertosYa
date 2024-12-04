package com.chaweDev.conciertosYa.service.visual;
import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurSeatsDTO;

public interface ISeatManagementService {
    OurSeatsDTO addSeat(DTO seat);
    OurSeatsDTO getAllSeats();
    OurSeatsDTO getSeatById(Integer seatId);
    OurSeatsDTO getSeatPlaceById(Integer placeId);
    OurSeatsDTO updateSeat(Integer seatId, DTO seat);
    OurSeatsDTO deleteSeat(Integer seatId);
}
