package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.PaymentMethodDTO;
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

    public PaymentMethodDTO addPaymentMethod(PaymentMethodDTO paymentMethod) {
        PaymentMethodDTO response = new PaymentMethodDTO();
        try {
            PaymentMethod savedPaymentMethod = new PaymentMethod();

            savedPaymentMethod.setType(paymentMethod.getType());
            savedPaymentMethod.setClient(paymentMethod.getClient());

            PaymentMethod ourPaymentMethodResult = paymentMethodRepo.save(savedPaymentMethod);
            if (ourPaymentMethodResult.getId() > 0) {
                response.setPaymentMethod(ourPaymentMethodResult);
                response.setMessage("Payment method Saved Successfully");
                response.setStatusCode(200);
            } else {
                response.setMessage("Payment method not saved due to an unknown error.");
                response.setStatusCode(500);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public PaymentMethodDTO getAllPaymentMethods() {
        PaymentMethodDTO response = new PaymentMethodDTO();
        try {
            List<PaymentMethod> paymentMethods = paymentMethodRepo.findAll();
            if (paymentMethods.isEmpty()) {
                response.setStatusCode(404);
                response.setMessage("No payment methods found");
            } else {
                response.setStatusCode(200);
                response.setOurPaymentMethodList(paymentMethods);
                response.setMessage("Payment methods found successfully");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred: " + e.getMessage());
        }
        return response;
    }

    public PaymentMethodDTO getPaymentMethodById(Integer paymentMethodId) {
        PaymentMethodDTO response = new PaymentMethodDTO();
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

    public PaymentMethodDTO updatePaymentMethod(Integer paymentMethodId, PaymentMethod paymentMethod) {
        PaymentMethodDTO response = new PaymentMethodDTO();
        try {
            Optional<PaymentMethod> existingPaymentMethodOpt = paymentMethodRepo.findById(paymentMethodId);
            if (existingPaymentMethodOpt.isPresent()) {
                PaymentMethod existingPaymentMethod = existingPaymentMethodOpt.get();

                existingPaymentMethod.setType(paymentMethod.getType());
                existingPaymentMethod.setClient(paymentMethod.getClient());

                PaymentMethod ourPaymentMethodResult = paymentMethodRepo.save(existingPaymentMethod);
                if (ourPaymentMethodResult.getId() > 0) {
                    response.setPaymentMethod(ourPaymentMethodResult);
                    response.setMessage("Payment method updated Successfully");
                    response.setStatusCode(200);
                } else {
                    response.setMessage("Payment method not updated due to an unknown error.");
                    response.setStatusCode(500);
                }
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

    public PaymentMethodDTO deletePaymentMethod(Integer paymentMethodId) {
        PaymentMethodDTO response = new PaymentMethodDTO();
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
}