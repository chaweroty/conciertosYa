package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.InvoiceDetailDTO;
import com.chaweDev.conciertosYa.entity.InvoiceDetail;
import com.chaweDev.conciertosYa.service.InvoiceDetailManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices_details")
public class InvoiceDetailManagementController {

    @Autowired
    private InvoiceDetailManagementService invoiceDetailManagementService;

    @PostMapping("/add")
    public ResponseEntity<InvoiceDetailDTO> addInvoiceDetail(@RequestBody InvoiceDetailDTO invoice) {
        InvoiceDetailDTO response = invoiceDetailManagementService.addInvoiceDetail(invoice);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<InvoiceDetailDTO> getAllInvoiceDetails() {
        InvoiceDetailDTO response = invoiceDetailManagementService.getAllInvoiceDetails();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-invoice-tickets/{invoiceId}")
    public ResponseEntity<InvoiceDetailDTO> getInvoiceTicketsByInvoiceId(@PathVariable Integer invoiceId) {
        return ResponseEntity.ok(invoiceDetailManagementService.getInvoiceTicketsByInvoiceId(invoiceId));
    }


    @GetMapping("/get/{invoiceDetailId}")
    public ResponseEntity<InvoiceDetailDTO> getInvoiceDetailById(@PathVariable Integer invoiceDetailId) {
        return ResponseEntity.ok(invoiceDetailManagementService.getInvoiceDetailById(invoiceDetailId));
    }

    @PutMapping("/update/{invoiceDetailId}")
    public ResponseEntity<InvoiceDetailDTO> updateInvoiceDetail(@PathVariable Integer invoiceDetailId, @RequestBody InvoiceDetail invoice) {
        return ResponseEntity.ok(invoiceDetailManagementService.updateInvoiceDetail(invoiceDetailId, invoice));
    }

    @DeleteMapping("/delete/{invoiceDetailId}")
    public ResponseEntity<InvoiceDetailDTO> deleteInvoiceDetail(@PathVariable Integer invoiceDetailId) {
        return ResponseEntity.ok(invoiceDetailManagementService.deleteInvoiceDetail(invoiceDetailId));
    }
}