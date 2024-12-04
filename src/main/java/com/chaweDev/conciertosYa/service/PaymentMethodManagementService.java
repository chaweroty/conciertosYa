package com.chaweDev.conciertosYa.service;

import com.chaweDev.conciertosYa.dto.DTO;
import com.chaweDev.conciertosYa.dto.OurArtistsDTO;
import com.chaweDev.conciertosYa.dto.PaymentMethodDTO;
import com.chaweDev.conciertosYa.entity.OurUsers;
import com.chaweDev.conciertosYa.entity.PaymentMethod;
import com.chaweDev.conciertosYa.repository.PaymentMethodRepo;
import com.chaweDev.conciertosYa.repository.UsersRepo;
import com.chaweDev.conciertosYa.service.visual.IPaymentMethodManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodManagementService implements IPaymentMethodManagementService {

    @Autowired
    private PaymentMethodRepo paymentMethodRepo;

    @Autowired
    private UsersRepo usersRepo;

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
        PaymentMethodDTO response;
        try {
            if (dto instanceof PaymentMethodDTO paymentMethod) {
                PaymentMethod savedPaymentMethod = new PaymentMethod();

                // Asignación de datos del DTO al objeto PaymentMethod
                savedPaymentMethod.setType(paymentMethod.getType());

                // Convertir SimplifiedUserDTO de vuelta a OurUsers si es necesario
                if (paymentMethod.getClient() != null) {
                    OurUsers client = usersRepo.findById(paymentMethod.getClient().getId())
                            .orElseThrow(() -> new RuntimeException("Client not found"));
                    savedPaymentMethod.setClient(client);
                }

                // Resto del código sigue igual
                PaymentMethod ourPaymentMethodResult = paymentMethodRepo.save(savedPaymentMethod);

                if (ourPaymentMethodResult.getId() > 0) {
                    response = new PaymentMethodDTO.Builder()
                            .paymentMethod(ourPaymentMethodResult)
                            .message("Payment method Saved Successfully")
                            .statusCode(200)
                            .build();
                } else {
                    response = new PaymentMethodDTO.Builder()
                            .message("Payment method not saved due to an unknown error.")
                            .statusCode(500)
                            .build();
                }
            } else {
                response = new PaymentMethodDTO.Builder()
                        .message("Invalid DTO type")
                        .statusCode(400)
                        .build();
            }
        } catch (Exception e) {
            response = new PaymentMethodDTO.Builder()
                    .message("Error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
        return response;
    }

    public PaymentMethodDTO getAllPaymentMethods() {
        PaymentMethodDTO response;
        try {
            List<PaymentMethod> paymentMethods = paymentMethodRepo.findAll();
            if (paymentMethods.isEmpty()) {
                response = new PaymentMethodDTO.Builder()
                        .message("No payment methods found")
                        .statusCode(404)
                        .build();
            } else {
                response = new PaymentMethodDTO.Builder()
                        .ourPaymentMethodList(paymentMethods)
                        .message("Payment Methods found successfully")
                        .statusCode(200)
                        .build();
            }
        } catch (Exception e) {
            response = new PaymentMethodDTO.Builder()
                    .message("Error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
        return response;
    }

    public PaymentMethodDTO getPaymentMethodById(Integer paymentMethodId) {
        PaymentMethodDTO response;
        try {
            PaymentMethod paymentMethod = paymentMethodRepo.findById(paymentMethodId).orElseThrow(() -> new RuntimeException("Payment Method not found"));
            response = new PaymentMethodDTO.Builder()
                    .paymentMethod(paymentMethod)
                    .message("Payment Method found successfully")
                    .statusCode(200)
                    .build();
        } catch (Exception e) {
            response = new PaymentMethodDTO.Builder()
                    .message("Error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
        return response;
    }

    public PaymentMethodDTO updatePaymentMethod(Integer paymentMethodId, PaymentMethod paymentMethod) {
        PaymentMethodDTO response;
        try {
            Optional<PaymentMethod> existingPaymentMethodOpt = paymentMethodRepo.findById(paymentMethodId);
            if (existingPaymentMethodOpt.isPresent()) {
                PaymentMethod existingPaymentMethod = existingPaymentMethodOpt.get();

                existingPaymentMethod.setType(paymentMethod.getType());
                existingPaymentMethod.setClient(paymentMethod.getClient());

                PaymentMethod ourPaymentMethodResult = paymentMethodRepo.save(existingPaymentMethod);
                if (ourPaymentMethodResult.getId() > 0) {
                    response = new PaymentMethodDTO.Builder()
                            .paymentMethod(ourPaymentMethodResult)
                            .message("Payment method updated Successfully")
                            .statusCode(200)
                            .build();
                } else {
                    response = new PaymentMethodDTO.Builder()
                            .message("Payment method not updated due to an unknown error.")
                            .statusCode(500)
                            .build();
                }
            } else {
                response = new PaymentMethodDTO.Builder()
                        .message("Payment Method not found")
                        .statusCode(404)
                        .build();
            }
        } catch (Exception e) {
            response = new PaymentMethodDTO.Builder()
                    .message("Error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
        return response;
    }

    public PaymentMethodDTO deletePaymentMethod(Integer paymentMethodId) {
        PaymentMethodDTO response = new PaymentMethodDTO();
        try {
            Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepo.findById(paymentMethodId);
            if (paymentMethodOptional.isPresent()) {
                paymentMethodRepo.deleteById(paymentMethodId);
                response = new PaymentMethodDTO.Builder()
                        .message("Payment Method deleted successfully")
                        .statusCode(200)
                        .build();
            } else {
                response = new PaymentMethodDTO.Builder()
                        .message("Payment Method not found")
                        .statusCode(404)
                        .build();
            }
        } catch (Exception e) {
            response = new PaymentMethodDTO.Builder()
                    .message("Error occurred: " + e.getMessage())
                    .statusCode(500)
                    .build();
        }
        return response;
    }
}