package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.DTO;
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

    /*
    Instanciación de PaymentMethodDTO desde un objeto de tipo DTO:
    El metodo addPaymentMethod recibe un parámetro de tipo DTO, que podría ser cualquier objeto que implemente la interfaz DTO.
    La expresión instanceof PaymentMethodDTO verifica si el objeto dto es una instancia de PaymentMethodDTO,
    lo que permite que se pueda tratar específicamente como un objeto de tipo PaymentMethodDTO. Si el objeto es de ese tipo,
    el código dentro del bloque if se ejecuta.

    Asignación a una variable del tipo PaymentMethodDTO:
    El uso de instanceof no solo verifica el tipo del objeto,
    sino que también realiza un casting implícito del objeto dto a PaymentMethodDTO.
    Esto significa que el objeto dto es tratado como un PaymentMethodDTO dentro del bloque if.
    Esta técnica permite que se puedan acceder de manera directa a los métodos y propiedades específicos de PaymentMethodDTO sin necesidad de hacer un cast manual,
    y es posible gracias al polimorfismo.

    Utilización de datos para crear y guardar un metodo de pago:
    Una vez verificado y casteado el DTO como PaymentMethodDTO, se procede a crear un objeto de tipo PaymentMethod
    y a establecer sus valores mediante los datos provenientes del DTO. Luego, el metodo de pago se guarda en el repositorio mediante paymentMethodRepo.save().
    Si el metodo de pago se guarda exitosamente (es decir, si el id del metodo de pago es mayor a 0), se actualizan los valores de la respuesta y se indica que el metodo de pago fue guardado correctamente.
    En caso contrario, se devuelve un mensaje indicando un error en el proceso de guardado.
    */

    public PaymentMethodDTO addPaymentMethod(DTO dto) {
        PaymentMethodDTO response = new PaymentMethodDTO();
        try {
            if (dto instanceof PaymentMethodDTO paymentMethod) {
                PaymentMethod savedPaymentMethod = new PaymentMethod();

                // Asignación de datos del DTO al objeto PaymentMethod
                savedPaymentMethod.setType(paymentMethod.getType());
                savedPaymentMethod.setClient(paymentMethod.getClient());

                // Guardado en el repositorio
                PaymentMethod ourPaymentMethodResult = paymentMethodRepo.save(savedPaymentMethod);

                // Verificación de éxito en el guardado
                if (ourPaymentMethodResult.getId() > 0) {
                    response.setPaymentMethod(ourPaymentMethodResult);
                    response.setMessage("Payment method Saved Successfully");
                    response.setStatusCode(200);
                } else {
                    response.setMessage("Payment method not saved due to an unknown error.");
                    response.setStatusCode(500);
                }
            } else {
                response.setMessage("Invalid DTO type");
                response.setStatusCode(400);
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