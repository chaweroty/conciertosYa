package com.chaweDev.conciertosYa.service.visual;
import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurTicketsDTO;
import com.chaweDev.conciertosYa.entity.OurTickets;

public interface ITicketManagementService {
    OurTicketsDTO addTicket(DTO ticket);

    OurTicketsDTO getAllTickets();

    OurTicketsDTO getTicketById(Integer ticketId);

    OurTicketsDTO updateTicket(Integer ticketId, OurTickets ticket);

    OurTicketsDTO deleteTicket(Integer ticketId);
}
