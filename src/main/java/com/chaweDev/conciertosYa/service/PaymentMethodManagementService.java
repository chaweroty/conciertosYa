/** package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.ReqRes;
import com.chaweDev.conciertosYa.entity.PaymentMethod;
import com.chaweDev.conciertosYa.repository.PaymentMethodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodManagementService {

    @Autowired
    private PaymentMethodRepo paymentMethodRepo;

    public ReqRes addPaymentMethod(PaymentMethod paymentMethod) {
        ReqRes response = new ReqRes();
        try {
            PaymentMethod savedPaymentMethod = paymentMethodRepo.save(paymentMethod);
            response.setStatusCode(200);
            response.setMessage("Payment Method added successfully");
            response.setPaymentMethod(savedPaymentMethod);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes getAllPaymentMethods() {
        ReqRes response = new ReqRes();
        try {
            List<PaymentMethod> paymentMethods = paymentMethodRepo.findAll();
            if (paymentMethods.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No payment methods found");
            } else {
                response.setStatusCode(200);
                response.setPaymentMethodList(paymentMethods);
                response.setMessage("Payment methods found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes getPaymentMethodById(Integer paymentMethodId) {
        ReqRes response = new ReqRes();
        try {
            PaymentMethod paymentMethod = paymentMethodRepo.findById(paymentMethodId).orElseThrow(() -> new RuntimeException("Payment Method not found"));
            response.setStatusCode(200);
            response.setPaymentMethod(paymentMethod);
            response.setMessage("Payment Method found successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes updatePaymentMethod(Integer paymentMethodId, PaymentMethod paymentMethod) {
        ReqRes response = new ReqRes();
        try {
            Optional<PaymentMethod> existingPaymentMethodOpt = paymentMethodRepo.findById(paymentMethodId);
            if (existingPaymentMethodOpt.isPresent()) {
                PaymentMethod existingPaymentMethod = existingPaymentMethodOpt.get();
                existingPaymentMethod.setType(paymentMethod.getType());
                existingPaymentMethod.setProvider(paymentMethod.getProvider());

                PaymentMethod updatedPaymentMethod = paymentMethodRepo.save(existingPaymentMethod);
                response.setStatusCode(200);
                response.setPaymentMethod(updatedPaymentMethod);
                response.setMessage("Payment Method updated successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Payment Method not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public ReqRes deletePaymentMethod(Integer paymentMethodId) {
        ReqRes response = new ReqRes();
        try {
            Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepo.findById(paymentMethodId);
            if (paymentMethodOptional.isPresent()) {
                paymentMethodRepo.deleteById(paymentMethodId);
                response.setStatusCode(200);
                response.setMessage("Payment Method deleted successfully");
            } else {
                response.setStatusCode(404);
                response.setMessage("Payment Method not found");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while deleting payment method: " + e.getMessage());
        }
        return response;
    }
} **/