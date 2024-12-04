package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.InvoiceDTO;
import com.chaweDev.conciertosYa.dto.InvoiceRequestDTO;
import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.service.InvoiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Principio aplicado: Controladores RESTful
// Este controlador implementa operaciones RESTful relacionadas con las facturas,
// siguiendo el patrón de diseño REST para facilitar la interacción con el cliente.
// La anotación @RestController organiza las rutas de forma clara y estructurada,
// asegurando consistencia en la implementación.
@RestController
@RequestMapping("/invoices")
public class InvoiceManagementController {

    // Principio aplicado: Inversión de Dependencias (DIP)
    // La dependencia se inyecta a través de la interfaz IInvoiceManagementService, lo que
    // permite cambiar implementaciones sin afectar la lógica del controlador.
    @Autowired
    private InvoiceManagementService invoiceManagementService;

    // Principio aplicado: Separación de Responsabilidades (SRP)
    // Este metodo se centra exclusivamente en procesar y delegar la lógica de negocio
    // para agregar una nueva factura.
    @PostMapping("/add")
    public ResponseEntity<InvoiceRequestDTO> addInvoice(@RequestBody InvoiceRequestDTO request) {
        InvoiceRequestDTO response = invoiceManagementService.addInvoice(request);
        System.out.println(ResponseEntity.status(response.getStatusCode()).body(response));
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: Cohesión Alta
    // Este metodo delega al servicio la recuperación de todas las facturas,
    // asegurando claridad en la separación de responsabilidades.
    @GetMapping("/get-all")
    public ResponseEntity<InvoiceDTO> getAllInvoices() {
        InvoiceDTO response = invoiceManagementService.getAllInvoices();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: Abierto/Cerrado (OCP)
    // Este metodo permite extender la funcionalidad de consulta sin modificar su estructura básica,
    // asegurando que se adapte a nuevas necesidades.
    @GetMapping("/get/{invoiceId}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Integer invoiceId) {
        return ResponseEntity.ok(invoiceManagementService.getInvoiceById(invoiceId));
    }

    @GetMapping("/get-user-invoices/{userId}")
    public ResponseEntity<InvoiceDTO> getInvoicesByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(invoiceManagementService.getInvoicesByUserId(userId));
    }

    // Principio aplicado: Reutilización de Código
    // El servicio encapsula la lógica para actualizar una factura,
    // lo que permite mantener el controlador limpio y enfocado.
    @PutMapping("/update/{invoiceId}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Integer invoiceId, @RequestBody Invoice invoice) {
        return ResponseEntity.ok(invoiceManagementService.updateInvoice(invoiceId, invoice));
    }

    // Principio aplicado: Responsabilidad Única
    // Este metodo está específicamente diseñado para manejar la eliminación de una factura,
    // asegurando que el controlador esté enfocado en su propósito principal.
    @DeleteMapping("/delete/{invoiceId}")
    public ResponseEntity<InvoiceDTO> deleteInvoice(@PathVariable Integer invoiceId) {
        return ResponseEntity.ok(invoiceManagementService.deleteInvoice(invoiceId));
    }
}