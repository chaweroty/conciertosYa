package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurArtists;
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
public class OurArtistsDTO extends DTO {

    private String name;
    private String musicalGenre;
    private String instagram;
    private String facebook;
    private String contact;

    private OurArtists ourArtists;
    private List<OurArtists> ourArtistsList;

    // Constructor por defecto
    public OurArtistsDTO() {
    }

    // Constructor con todos los campos
    @JsonCreator
    public OurArtistsDTO(
            @JsonProperty("statusCode") int statusCode,
            @JsonProperty("error") String error,
            @JsonProperty("message") String message,
            @JsonProperty("name") String name,
            @JsonProperty("musicalGenre") String musicalGenre,
            @JsonProperty("instagram") String instagram,
            @JsonProperty("facebook") String facebook,
            @JsonProperty("contact") String contact,
            @JsonProperty("ourArtists") OurArtists ourArtists,
            @JsonProperty("ourArtistsList") List<OurArtists> ourArtistsList
    ) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.name = name;
        this.musicalGenre = musicalGenre;
        this.instagram = instagram;
        this.facebook = facebook;
        this.contact = contact;
        this.ourArtists = ourArtists;
        this.ourArtistsList = ourArtistsList;
    }

    // Constructor privado para usar el patrón Builder
    private OurArtistsDTO(Builder builder) {
        this.statusCode = builder.statusCode;
        this.error = builder.error;
        this.message = builder.message;
        this.name = builder.name;
        this.musicalGenre = builder.musicalGenre;
        this.instagram = builder.instagram;
        this.facebook = builder.facebook;
        this.contact = builder.contact;
        this.ourArtists = builder.ourArtists;
        this.ourArtistsList = builder.ourArtistsList;
    }

    // Builder estático
    public static class Builder {
        // Campos de DTO (clase padre)
        private int statusCode;
        private String error;
        private String message;

        // Campos específicos de OurArtistsDTO
        private String name;
        private String musicalGenre;
        private String instagram;
        private String facebook;
        private String contact;
        private OurArtists ourArtists;
        private List<OurArtists> ourArtistsList;

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

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder musicalGenre(String musicalGenre) {
            this.musicalGenre = musicalGenre;
            return this;
        }

        public Builder instagram(String instagram) {
            this.instagram = instagram;
            return this;
        }

        public Builder facebook(String facebook) {
            this.facebook = facebook;
            return this;
        }

        public Builder contact(String contact) {
            this.contact = contact;
            return this;
        }

        public Builder ourArtists(OurArtists ourArtists) {
            this.ourArtists = ourArtists;
            return this;
        }

        public Builder ourArtistsList(List<OurArtists> ourArtistsList) {
            this.ourArtistsList = ourArtistsList;
            return this;
        }

        // Metodo para construir la instancia
        public OurArtistsDTO build() {
            return new OurArtistsDTO(this);
        }
    }
}