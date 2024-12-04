package com.chaweDev.conciertosYa.controller;

import com.chaweDev.conciertosYa.service.visual.IPaymentMethodManagementService;
import com.chaweDev.conciertosYa.dto.PaymentMethodDTO;
import com.chaweDev.conciertosYa.entity.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Principio aplicado: Controladores RESTful
// Este controlador sigue el patrón REST para manejar solicitudes HTTP relacionadas con métodos de pago.
// El uso de @RestController y @RequestMapping garantiza que las rutas estén organizadas y fáciles de entender,
// aplicando el principio de diseño de interfaces claras y consistentes.
@RestController
@RequestMapping("/payment_method")
public class PaymentMethodManagementController {

    // Principio aplicado: Inversión de Dependencias (DIP)
    // Aquí se aplica la inversión de dependencias al inyectar una interfaz (IPaymentMethodManagementService)
    // en lugar de una implementación concreta. Esto facilita el cambio de implementación en el futuro y
    // mejora la testabilidad de este controlador.
    @Autowired
    private IPaymentMethodManagementService paymentMethodManagementService;

    // Principio aplicado: Separación de Responsabilidades (SRP)
    // cada metodo solo maneja una solicitud HTTP y delega toda la lógica de negocio al servicio.
    // Esto evita que el controlador tenga múltiples responsabilidades, manteniéndolo limpio y fácil de mantener.
    @PostMapping("/add")
    public ResponseEntity<PaymentMethodDTO> addPaymentMethod(@RequestBody PaymentMethodDTO paymentMethod) {
        PaymentMethodDTO response = paymentMethodManagementService.addPaymentMethod(paymentMethod);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: DRY (Don't Repeat Yourself)
    // Este metodo usa el servicio para obtener todos los métodos de pago sin duplicar la lógica de negocio.
    @GetMapping("/get-all")
    public ResponseEntity<PaymentMethodDTO> getAllPaymentMethods() {
        PaymentMethodDTO response = paymentMethodManagementService.getAllPaymentMethods();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Principio aplicado: Abierto/Cerrado (OCP)
    // Este metodo es extensible para manejar diferentes tipos de consultas por ID sin modificar su estructura,
    // siempre y cuando el servicio gestione adecuadamente la lógica interna.
    @GetMapping("/get/{paymentMethodId}")
    public ResponseEntity<PaymentMethodDTO> getPaymentMethodById(@PathVariable Integer paymentMethodId) {
        return ResponseEntity.ok(paymentMethodManagementService.getPaymentMethodById(paymentMethodId));
    }

    // Principio aplicado: Cohesión Alta
    // Este metodo es específico para actualizar un metodo de pago, manteniendo su responsabilidad clara y única.
    // Se delega la lógica de actualización al servicio, manteniendo la cohesión del controlador.
    @PutMapping("/update/{paymentMethodId}")
    public ResponseEntity<PaymentMethodDTO> updatePaymentMethod(@PathVariable Integer paymentMethodId, @RequestBody PaymentMethod paymentMethod) {
        return ResponseEntity.ok(paymentMethodManagementService.updatePaymentMethod(paymentMethodId, paymentMethod));
    }

    // Principio aplicado: Responsabilidad Única
    // Este metodo está específicamente diseñado para manejar la eliminación de un metodo de pago,
    // asegurando que el controlador esté enfocado en su propósito principal.
    @DeleteMapping("/delete/{paymentMethodId}")
    public ResponseEntity<PaymentMethodDTO> deletePaymentMethod(@PathVariable Integer paymentMethodId) {
        return ResponseEntity.ok(paymentMethodManagementService.deletePaymentMethod(paymentMethodId));
    }
}