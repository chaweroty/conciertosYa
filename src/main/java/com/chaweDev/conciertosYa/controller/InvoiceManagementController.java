package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.InvoiceDTO;
import com.chaweDev.conciertosYa.dto.InvoiceDetailDTO;
import com.chaweDev.conciertosYa.dto.InvoiceRequestDTO;
import com.chaweDev.conciertosYa.dto.OurTicketsDTO;
import com.chaweDev.conciertosYa.entity.Invoice;
import com.chaweDev.conciertosYa.service.InvoiceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceManagementController {

    @Autowired
    private InvoiceManagementService invoiceManagementService;

    @PostMapping("/add")
    public ResponseEntity<InvoiceDTO> addInvoice(@RequestBody InvoiceRequestDTO request) {
        InvoiceDTO response = invoiceManagementService.addInvoice(request);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<InvoiceDTO> getAllInvoices() {
        InvoiceDTO response = invoiceManagementService.getAllInvoices();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{invoiceId}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Integer invoiceId) {
        return ResponseEntity.ok(invoiceManagementService.getInvoiceById(invoiceId));
    }

    @PutMapping("/update/{invoiceId}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable Integer invoiceId, @RequestBody Invoice invoice) {
        return ResponseEntity.ok(invoiceManagementService.updateInvoice(invoiceId, invoice));
    }

    @DeleteMapping("/delete/{invoiceId}")
    public ResponseEntity<InvoiceDTO> deleteInvoice(@PathVariable Integer invoiceId) {
        return ResponseEntity.ok(invoiceManagementService.deleteInvoice(invoiceId));
    }
}