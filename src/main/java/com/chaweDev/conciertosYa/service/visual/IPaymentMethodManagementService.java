package com.chaweDev.conciertosYa.service.visual;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.PaymentMethodDTO;
import com.chaweDev.conciertosYa.entity.PaymentMethod;

public interface IPaymentMethodManagementService {
    PaymentMethodDTO addPaymentMethod(DTO paymentMethod);

    PaymentMethodDTO getAllPaymentMethods();

    PaymentMethodDTO getPaymentMethodById(Integer paymentMethodId);

    PaymentMethodDTO updatePaymentMethod(Integer paymentMethodId, PaymentMethod paymentMethod);

    PaymentMethodDTO deletePaymentMethod(Integer paymentMethodId);
}
