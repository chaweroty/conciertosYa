package com.chaweDev.conciertosYa.controller.Visual;
import com.chaweDev.conciertosYa.dto.OurTicketsDTO;
import com.chaweDev.conciertosYa.entity.OurTickets;

public interface ITicketManagementService {
    OurTicketsDTO addTicket(OurTicketsDTO ticket);

    OurTicketsDTO getAllTickets();

    OurTicketsDTO getTicketById(Integer ticketId);

    OurTicketsDTO updateTicket(Integer ticketId, OurTickets ticket);

    OurTicketsDTO deleteTicket(Integer ticketId);
}
