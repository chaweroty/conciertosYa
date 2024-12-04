package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurUsers;
import com.chaweDev.conciertosYa.entity.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentMethodDTO extends DTO {

    private Integer id;
    private String type; // Cash, Cash and Credit Card, etc.
    private SimplifiedUserDTO client;
    private PaymentMethod paymentMethod;
    private List<PaymentMethod> ourPaymentMethodList;

    // Constructor por defecto
    public PaymentMethodDTO() {
    }

    // Metodo de conversión de OurUsers a SimplifiedUserDTO
    private SimplifiedUserDTO convertToSimplifiedUser(OurUsers user) {
        if (user == null) return null;
        return new SimplifiedUserDTO(user.getId(), user.getUsername());
    }

    // Constructor con todos los campos
    @JsonCreator
    public PaymentMethodDTO(
            @JsonProperty("statusCode") int statusCode,
            @JsonProperty("error") String error,
            @JsonProperty("message") String message,
            @JsonProperty("id") Integer id,
            @JsonProperty("type") String type,
            @JsonProperty("client") OurUsers client,
            @JsonProperty("paymentMethod") PaymentMethod paymentMethod,
            @JsonProperty("ourPaymentMethodList") List<PaymentMethod> ourPaymentMethodList
    ) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.id = id;
        this.type = type;
        this.client = convertToSimplifiedUser(client);
        this.paymentMethod = paymentMethod;
        this.ourPaymentMethodList = ourPaymentMethodList;
    }

    // Constructor privado para usar el patrón Builder
    private PaymentMethodDTO(Builder builder) {
        this.statusCode = builder.statusCode;
        this.error = builder.error;
        this.message = builder.message;
        this.id = builder.id;
        this.type = builder.type;
        this.client = convertToSimplifiedUser(builder.client);
        this.paymentMethod = builder.paymentMethod;
        this.ourPaymentMethodList = builder.ourPaymentMethodList;
    }

    // Clase SimplifiedUserDTO para evitar problemas de serialización
    @Data
    @Getter
    @Setter
    public static class SimplifiedUserDTO {
        private Integer id;
        private String username;

        public SimplifiedUserDTO() {}

        public SimplifiedUserDTO(Integer id, String username) {
            this.id = id;
            this.username = username;
        }
    }

    // Builder estático
    public static class Builder {
        // Campos de DTO (clase padre)
        private int statusCode;
        private String error;
        private String message;

        // Campos específicos de PaymentMethodDTO
        private Integer id;
        private String type;
        private OurUsers client;
        private PaymentMethod paymentMethod;
        private List<PaymentMethod> ourPaymentMethodList;

        // Constructor por defecto para el Builder
        public Builder() {
        }

        // Métodos para configurar los valores (fluido)
        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder client(OurUsers client) {
            this.client = client;
            return this;
        }

        public Builder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder ourPaymentMethodList(List<PaymentMethod> ourPaymentMethodList) {
            this.ourPaymentMethodList = ourPaymentMethodList;
            return this;
        }

        // Metodo para construir la instancia
        public PaymentMethodDTO build() {
            return new PaymentMethodDTO(this);
        }
    }
}