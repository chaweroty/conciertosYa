package com.chaweDev.conciertosYa.controller.Visual;

import com.chaweDev.conciertosYa.dto.PaymentMethodDTO;
import com.chaweDev.conciertosYa.entity.PaymentMethod;

public interface IPaymentMethodManagementService {
    PaymentMethodDTO addPaymentMethod(PaymentMethodDTO paymentMethod);

    PaymentMethodDTO getAllPaymentMethods();

    PaymentMethodDTO getPaymentMethodById(Integer paymentMethodId);

    PaymentMethodDTO updatePaymentMethod(Integer paymentMethodId, PaymentMethod paymentMethod);

    PaymentMethodDTO deletePaymentMethod(Integer paymentMethodId);
}
