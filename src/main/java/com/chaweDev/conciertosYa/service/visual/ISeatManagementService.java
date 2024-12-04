package com.chaweDev.conciertosYa.service.Visual;
import com.chaweDev.conciertosYa.dto.OurSeatsDTO;

public interface ISeatManagementService {
    OurSeatsDTO addSeat(OurSeatsDTO seat);
    OurSeatsDTO getAllSeats();
    OurSeatsDTO getSeatById(Integer seatId);
    OurSeatsDTO updateSeat(Integer seatId, OurSeatsDTO seat);
    OurSeatsDTO deleteSeat(Integer seatId);
}
