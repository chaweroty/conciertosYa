package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.OurSeatsDTO;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.entity.OurSeats;
import com.chaweDev.conciertosYa.repository.SeatRepo;
import com.chaweDev.conciertosYa.repository.PlaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatManagementService {

    @Autowired
    private SeatRepo seatRepo;
    @Autowired
    private PlaceRepo placeRepo;

    public OurSeatsDTO addSeat(OurSeatsDTO seat) {
        OurSeatsDTO response = new OurSeatsDTO();
        try {
            OurSeats ourSeat = new OurSeats();

            saveSeat(seat, ourSeat);

            OurSeats ourSeatResult = seatRepo.save(ourSeat);
            if (ourSeatResult.getId() > 0) {
                response.setOurSeats(ourSeatResult);
                response.setMessage("Seat Saved Successfully");
                response.setStatusCode(200);
            } else {
                response.setMessage("Seat not saved due to an unknown error.");
                response.setStatusCode(500);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurSeatsDTO getAllSeats() {
        OurSeatsDTO response = new OurSeatsDTO();
        try {
            List<OurSeats> seats = seatRepo.findAll();
            if (seats.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No seats found");
            } else {
                response.setStatusCode(200);
                response.setOurSeatsList(seats);
                response.setMessage("Seats found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurSeatsDTO getSeatById(Integer seatId) {
        OurSeatsDTO response = new OurSeatsDTO();
        try {
            OurSeats seat = seatRepo.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found"));
            response.setStatusCode(200);
            response.setOurSeats(seat);
            response.setMessage("Seat found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurSeatsDTO updateSeat(Integer seatId, OurSeatsDTO seat) {
        OurSeatsDTO response = new OurSeatsDTO();
        try {
            Optional<OurSeats> existingSeatOpt = seatRepo.findById(seatId);
            if (existingSeatOpt.isPresent()) {
                OurSeats existingSeat = existingSeatOpt.get();

                saveSeat(seat, existingSeat);

                OurSeats updatedSeat = seatRepo.save(existingSeat);

                response.setStatusCode(200);
                response.setOurSeats(updatedSeat);
                response.setMessage("Seat updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Seat not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    private void saveSeat(OurSeatsDTO seat, OurSeats existingSeat) {
        existingSeat.setCode(seat.getCode());
        existingSeat.setRow(seat.getRow());
        existingSeat.setColumn(seat.getColumn());
        existingSeat.setPrice(seat.getPrice());
        existingSeat.setDiscount(seat.getDiscount());
        existingSeat.setType(seat.getType());
        existingSeat.setState(seat.getState());

        OurPlaces place = placeRepo.findById(seat.getPlace())
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        existingSeat.setPlace(place);
    }

    public OurSeatsDTO deleteSeat(Integer seatId) {
        OurSeatsDTO response = new OurSeatsDTO();
        try {
            Optional<OurSeats> seatOptional = seatRepo.findById(seatId);
            if (seatOptional.isPresent()) {
                seatRepo.deleteById(seatId);
                response.setStatusCode(200);
                response.setMessage("Seat deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Seat not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting seat: " + e.getMessage());
        }
        return response;
    }
}