/**package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.ReqRes;
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
    public ResponseEntity<ReqRes> addInvoice(@RequestBody Invoice invoice) {
        ReqRes response = invoiceManagementService.addInvoice(invoice);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ReqRes> getAllInvoices() {
        ReqRes response = invoiceManagementService.getAllInvoices();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{invoiceId}")
    public ResponseEntity<ReqRes> getInvoiceById(@PathVariable Integer invoiceId) {
        return ResponseEntity.ok(invoiceManagementService.getInvoiceById(invoiceId));
    }

    @PutMapping("/update/{invoiceId}")
    public ResponseEntity<ReqRes> updateInvoice(@PathVariable Integer invoiceId, @RequestBody Invoice invoice) {
        return ResponseEntity.ok(invoiceManagementService.updateInvoice(invoiceId, invoice));
    }

    @DeleteMapping("/delete/{invoiceId}")
    public ResponseEntity<ReqRes> deleteInvoice(@PathVariable Integer invoiceId) {
        return ResponseEntity.ok(invoiceManagementService.deleteInvoice(invoiceId));
    }
} **/