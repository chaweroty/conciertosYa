package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.dto.PaymentMethodDTO;
import com.chaweDev.conciertosYa.entity.PaymentMethod;
import com.chaweDev.conciertosYa.service.PaymentMethodManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment_method")
public class PaymentMethodManagementController {

    @Autowired
    private PaymentMethodManagementService paymentMethodManagementService;

    @PostMapping("/add")
    public ResponseEntity<PaymentMethodDTO> addPaymentMethod(@RequestBody PaymentMethodDTO paymentMethod) {
        PaymentMethodDTO response = paymentMethodManagementService.addPaymentMethod(paymentMethod);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<PaymentMethodDTO> getAllPaymentMethods() {
        PaymentMethodDTO response = paymentMethodManagementService.getAllPaymentMethods();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/get/{paymentMethodId}")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethodById(@PathVariable Integer paymentMethodId) {
        return ResponseEntity.ok(paymentMethodManagementService.getPaymentMethodById(paymentMethodId));
    }

    @PutMapping("/update/{paymentMethodId}")
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(@PathVariable Integer paymentMethodId, @RequestBody PaymentMethod paymentMethod) {
        return ResponseEntity.ok(paymentMethodManagementService.updatePaymentMethod(paymentMethodId, paymentMethod));
    }

    @DeleteMapping("/delete/{paymentMethodId}")
    public ResponseEntity<PaymentMethodDTO> deletePaymentMethod(@PathVariable Integer paymentMethodId) {
        return ResponseEntity.ok(paymentMethodManagementService.deletePaymentMethod(paymentMethodId));
    }
}