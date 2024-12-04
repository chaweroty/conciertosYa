package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.service.visual.IInvoiceDetailManagementService;
import com.chaweDev.conciertosYa.dto.InvoiceDetailDTO;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Principio aplicado: Controladores RESTful
// Este controlador sigue el patrón REST para manejar solicitudes HTTP relacionadas con los detalles de facturas.
// El uso de @RestController y @RequestMapping organiza las rutas de manera clara y consistente,
// promoviendo interfaces intuitivas y estandarizadas.
@RestController
@RequestMapping("/invoices_details")
public class InvoiceDetailManagementController {

    // Principio aplicado: Inversión de Dependencias (DIP)
    // Al inyectar una interfaz (IInvoiceDetailManagementService) en lugar de una implementación concreta,
    // se permite mayor flexibilidad y capacidad de prueba en el código.
    @Autowired
    private IInvoiceDetailManagementService invoiceDetailManagementService;

    // Principio aplicado: Separación de Responsabilidades (SRP)
    // Este metodo maneja exclusivamente la lógica de adición de un detalle de factura,
    // delegando las operaciones de negocio al servicio correspondiente.
    @PostMapping("/add")
    public ResponseEntity<InvoiceDetailDTO> addInvoiceDetail(@RequestBody InvoiceDetailDTO invoice) {
        InvoiceDetailDTO response = invoiceDetailManagementService.addInvoiceDetail(invoice);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: DRY (Don't Repeat Yourself)
    // Este metodo reutiliza la lógica definida en el servicio para obtener todos los detalles de facturas,
    // evitando redundancia en el controlador.
    @GetMapping("/get-all")
    public ResponseEntity<InvoiceDetailDTO> getAllInvoiceDetails() {
        InvoiceDetailDTO response = invoiceDetailManagementService.getAllInvoiceDetails();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-invoice-tickets/{invoiceId}")
    public ResponseEntity<InvoiceDetailDTO> getInvoiceTicketsByInvoiceId(@PathVariable Integer invoiceId) {
        return ResponseEntity.ok(invoiceDetailManagementService.getInvoiceDetailById(invoiceId));
    }

    // Principio aplicado: Abierto/Cerrado (OCP)
    // Este metodo está diseñado para ser extensible, permitiendo gestionar diferentes criterios
    // de consulta para un detalle de factura sin modificar su estructura básica.
    @GetMapping("/get/{invoiceDetailId}")
    public ResponseEntity<InvoiceDetailDTO> getInvoiceDetailById(@PathVariable Integer invoiceDetailId) {
        return ResponseEntity.ok(invoiceDetailManagementService.getInvoiceDetailById(invoiceDetailId));
    }

    // Principio aplicado: Cohesión Alta
    // Este metodo se encarga exclusivamente de actualizar los detalles de una factura,
    // manteniendo la claridad y enfoque del controlador.
    @PutMapping("/update/{invoiceDetailId}")
    public ResponseEntity<InvoiceDetailDTO> updateInvoiceDetail(@PathVariable Integer invoiceDetailId, @RequestBody InvoiceDetail invoice) {
        return ResponseEntity.ok(invoiceDetailManagementService.updateInvoiceDetail(invoiceDetailId, invoice));
    }

    // Principio aplicado: Responsabilidad Única
    // Este metodo está específicamente diseñado para manejar la eliminación de un detalle de factura,
    // asegurando que el controlador esté enfocado en su propósito principal.
    @DeleteMapping("/delete/{invoiceDetailId}")
    public ResponseEntity<InvoiceDetailDTO> deleteInvoiceDetail(@PathVariable Integer invoiceDetailId) {
        return ResponseEntity.ok(invoiceDetailManagementService.deleteInvoiceDetail(invoiceDetailId));
    }
}