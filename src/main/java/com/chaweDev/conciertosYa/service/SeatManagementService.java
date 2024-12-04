package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurSeatsDTO;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.chaweDev.conciertosYa.entity.OurSeats;
import com.chaweDev.conciertosYa.repository.SeatRepo;
import com.chaweDev.conciertosYa.repository.PlaceRepo;
import com.chaweDev.conciertosYa.service.visual.ISeatManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// PRINCIPIO DE EXPERTO EN INFORMACIÓN (Information Expert):
// El servicio es el experto en la gestión de los asientos.
// Contiene la lógica para manipular los datos relacionados con la gestion de los asientos,
// como guardarlos, obtenerlos, actualizarlos y eliminarlos, y tiene acceso
// al repositorio donde se encuentran los datos de los asientos.
@Service
public class SeatManagementService implements ISeatManagementService {

    @Autowired
    private SeatRepo seatRepo;
    @Autowired
    private PlaceRepo placeRepo;

    // PRINCIPIO DE CREADOR (Creator):
    // Este metodo crea un nuevo asiento utilizando los datos recibidos en el DTO.
    // El servicio se encarga de crear el objeto  a partir de los datos recibidos,
    // lo guarda en el repositorio y luego genera una respuesta adecuada.
    /*
    Instanciación de OurSeatsDTO desde un objeto de tipo DTO:
    El metodo addSeat recibe un parámetro de tipo DTO, que podría ser cualquier objeto que implemente la interfaz DTO.
    La expresión instanceof OurSeatsDTO verifica si el objeto dto es una instancia de OurSeatsDTO,
    lo que permite que se pueda tratar específicamente como un objeto de tipo OurSeatsDTO. Si el objeto es de ese tipo,
    el código dentro del bloque if se ejecuta.

    Asignación a una variable del tipo OurSeatsDTO:
    El uso de instanceof no solo verifica el tipo del objeto,
    sino que también realiza un casting implícito del objeto dto a OurSeatsDTO.
    Esto significa que el objeto dto es tratado como un OurSeatsDTO dentro del bloque if.
    Esta técnica permite que se puedan acceder de manera directa a los métodos y propiedades específicos de OurSeatsDTO sin necesidad de hacer un cast manual,
    y es posible gracias al polimorfismo.

    Utilización de datos para crear y guardar un asiento:
    Una vez verificado y casteado el DTO como OurSeatsDTO, se procede a crear un objeto de tipo OurSeats
    y a establecer sus valores mediante los datos provenientes del DTO. Luego, el asiento se guarda en el repositorio mediante seatRepo.save().
    Si el asiento se guarda exitosamente (es decir, si el id del asiento es mayor a 0), se actualizan los valores de la respuesta y se indica que el asiento fue guardado correctamente.
    En caso contrario, se devuelve un mensaje indicando un error en el proceso de guardado.
    */

    public OurSeatsDTO addSeat(DTO dto) {
        OurSeatsDTO response = new OurSeatsDTO();
        try {
            if (dto instanceof OurSeatsDTO seat) {
                OurSeats ourSeat = new OurSeats();

                // Se asume que la función saveSeat toma el DTO y el objeto OurSeats para asignar sus valores.
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
            } else {
                response.setMessage("Invalid DTO type");
                response.setStatusCode(400);
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

    public OurSeatsDTO getSeatPlaceById(Integer placeId) {
        OurSeatsDTO response = new OurSeatsDTO();
        try {
            List<OurSeats> seats = seatRepo.findAll();
            List<OurSeats> placeSeats = new ArrayList<>();
            for (OurSeats seat : seats){
                if (Objects.equals(seat.getPlace().getId(), placeId)){
                    placeSeats.add(seat);
                }
            }
            if (seats.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No seats found");
            } else {
                response.setStatusCode(200);
                response.setOurSeatsList(placeSeats);
                response.setMessage("Seats found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public OurSeatsDTO updateSeat(Integer seatId, DTO seat) {
        OurSeatsDTO response = new OurSeatsDTO();
        try {
            Optional<OurSeats> existingSeatOpt = seatRepo.findById(seatId);
            if (existingSeatOpt.isPresent()) {
                OurSeats existingSeat = existingSeatOpt.get();

                saveSeat((OurSeatsDTO) seat, existingSeat);

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