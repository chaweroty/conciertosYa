package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.InvoiceDetailDTO;
import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;
import com.chaweDev.conciertosYa.entity.OurTickets;
import com.chaweDev.conciertosYa.repository.InvoiceDetailRepo;
import com.chaweDev.conciertosYa.service.visual.IInvoiceDetailManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceDetailManagementService implements IInvoiceDetailManagementService {

    @Autowired
    private InvoiceDetailRepo invoiceDetailRepo;

    /*
    Instanciación de InvoiceDetailDTO desde un objeto de tipo DTO:
    El metodo addInvoiceDetail recibe un parámetro de tipo DTO, que podría ser cualquier objeto que implemente la interfaz DTO.
    La expresión instanceof InvoiceDetailDTO verifica si el objeto invoiceDetail es una instancia de InvoiceDetailDTO,
    lo que permite que se pueda tratar específicamente como un objeto de tipo InvoiceDetailDTO. Si el objeto es de ese tipo,
    el código dentro del bloque if se ejecuta.

    Asignación a una variable del tipo InvoiceDetailDTO:
    El uso de instanceof no solo verifica el tipo del objeto,
    sino que también realiza un casting implícito del objeto invoiceDetail a InvoiceDetailDTO.
    Esto significa que el objeto invoiceDetail es tratado como un InvoiceDetailDTO dentro del bloque if.
    Esta técnica permite que se puedan acceder de manera directa a los métodos y propiedades específicos de InvoiceDetailDTO sin necesidad de hacer un cast manual,
    y es posible gracias al polimorfismo.

    Utilización de datos para crear y guardar un detalle de factura:
    Una vez verificado y casteado el DTO como InvoiceDetailDTO, se procede a crear un objeto de tipo InvoiceDetail
    y a establecer sus valores mediante los datos provenientes del DTO. Luego, el detalle de la factura se guarda en el repositorio mediante invoiceDetailRepo.save().
    Si el detalle de la factura se guarda exitosamente (es decir, si el id del detalle es mayor a 0), se actualizan los valores de la respuesta y se indica que el detalle de la factura fue guardado correctamente.
    En caso contrario, se devuelve un mensaje indicando un error en el proceso de guardado.
    */

    public InvoiceDetailDTO addInvoiceDetail(DTO dto) {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            if (dto instanceof InvoiceDetailDTO invoiceDetail) {
                InvoiceDetail savedInvoiceDetail = new InvoiceDetail();

                saveInvoiceDetail(savedInvoiceDetail, invoiceDetail.getTicket(), invoiceDetail.getInvoice());

                InvoiceDetail updatedInvoiceDetail = invoiceDetailRepo.save(savedInvoiceDetail);

                if (updatedInvoiceDetail.getId() > 0) {
                    response.setOurInvoiceDetail(updatedInvoiceDetail);
                    response.setMessage("Invoice detail Saved Successfully");
                    response.setStatusCode(200);
                } else {
                    response.setMessage("Invoice detail not saved due to an unknown error.");
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
    public InvoiceDetailDTO getAllInvoiceDetails() {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            List<InvoiceDetail> invoiceDetails = invoiceDetailRepo.findAll();
            if (invoiceDetails.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No invoice details found");
            } else {
                response.setStatusCode(200);
                response.setOurInvoicesDetailList(invoiceDetails);
                response.setMessage("Invoice details found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDetailDTO getInvoiceTicketsByInvoiceId(Integer invoiceId) {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            List<InvoiceDetail> invoiceDetails = invoiceDetailRepo.findAll();
            List<InvoiceDetail> invoiceTickets = new ArrayList<>();
            for (InvoiceDetail invoiceDetail : invoiceDetails){
                if(invoiceDetail.getInvoice().getId().equals(invoiceId)){
                    invoiceTickets.add(invoiceDetail);
                }
            }
            if (invoiceDetails.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No invoice details found");
            } else {
                response.setStatusCode(200);
                response.setOurInvoicesDetailList(invoiceTickets);
                response.setMessage("Invoice details found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDetailDTO getInvoiceDetailById(Integer invoiceDetailId) {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            InvoiceDetail invoiceDetail = invoiceDetailRepo.findById(invoiceDetailId).orElseThrow(() -> new RuntimeException("Invoice Detail not found"));
            response.setStatusCode(200);
            response.setOurInvoiceDetail(invoiceDetail);
            response.setMessage("Invoice Detail found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public InvoiceDetailDTO updateInvoiceDetail(Integer invoiceDetailId, InvoiceDetail invoiceDetail) {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            Optional<InvoiceDetail> existingInvoiceDetailOpt = invoiceDetailRepo.findById(invoiceDetailId);
            if (existingInvoiceDetailOpt.isPresent()) {
                InvoiceDetail existingInvoiceDetail = existingInvoiceDetailOpt.get();

                saveInvoiceDetail(existingInvoiceDetail, invoiceDetail.getTicket(), invoiceDetail.getInvoice());


                InvoiceDetail updatedInvoiceDetail = invoiceDetailRepo.save(existingInvoiceDetail);

                response.setStatusCode(200);
                response.setOurInvoiceDetail(updatedInvoiceDetail);
                response.setMessage("Invoice Detail updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Invoice Detail not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    private void saveInvoiceDetail(InvoiceDetail existingInvoiceDetail, OurTickets ticket, Invoice invoice) {
        existingInvoiceDetail.setTicket(ticket);
        existingInvoiceDetail.setInvoice(invoice);
    }

    public InvoiceDetailDTO deleteInvoiceDetail(Integer invoiceDetailId) {
        InvoiceDetailDTO response = new InvoiceDetailDTO();
        try {
            Optional<InvoiceDetail> invoiceDetailOptional = invoiceDetailRepo.findById(invoiceDetailId);
            if (invoiceDetailOptional.isPresent()) {
                invoiceDetailRepo.deleteById(invoiceDetailId);
                response.setStatusCode(200);
                response.setMessage("Invoice Detail deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Invoice Detail not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting invoice detail: " + e.getMessage());
        }
        return response;
    }
}