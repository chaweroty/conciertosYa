package com.chaweDev.conciertosYa.dto;

import com.chaweDev.conciertosYa.entity.OurArtists;
import com.chaweDev.conciertosYa.entity.OurEvents;
import com.chaweDev.conciertosYa.entity.OurPlaces;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OurEventsDTO extends DTO {

    private String name;
    private LocalDate date;
    private LocalTime hour;
    private String description;
    private String musicalGenre;
    private String status;
    private String image;

    // Relación con lugar
    private OurPlaces place;
    private OurArtists artist;

    private OurEvents ourEvents;
    private List<OurEvents> ourEventsList;

    // Constructor por defecto
    public OurEventsDTO() {}

    // Constructor privado para Builder
    private OurEventsDTO(Builder builder) {
        this.statusCode = builder.statusCode;
        this.error = builder.error;
        this.message = builder.message;
        this.name = builder.name;
        this.date = builder.date;
        this.hour = builder.hour;
        this.description = builder.description;
        this.musicalGenre = builder.musicalGenre;
        this.status = builder.status;
        this.image = builder.image;
        this.place = builder.place;
        this.artist = builder.artist;
        this.ourEvents = builder.ourEvents;
        this.ourEventsList = builder.ourEventsList;
    }

    // Builder estático
    public static class Builder {
        // Campos de DTO (clase padre)
        private int statusCode;
        private String error;
        private String message;

        // Campos específicos de OurEventsDTO
        private String name;
        private LocalDate date;
        private LocalTime hour;
        private String description;
        private String musicalGenre;
        private String status;
        private String image;
        private OurPlaces place;
        private OurArtists artist;
        private OurEvents ourEvents;
        private List<OurEvents> ourEventsList;

        // Constructor por defecto para el Builder
        public Builder() {}

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

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder hour(LocalTime hour) {
            this.hour = hour;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder musicalGenre(String musicalGenre) {
            this.musicalGenre = musicalGenre;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Builder place(OurPlaces place) {
            this.place = place;
            return this;
        }

        public Builder artist(OurArtists artist) {
            this.artist = artist;
            return this;
        }

        public Builder ourEvents(OurEvents ourEvents) {
            this.ourEvents = ourEvents;
            return this;
        }

        public Builder ourEventsList(List<OurEvents> ourEventsList) {
            this.ourEventsList = ourEventsList;
            return this;
        }

        // Metodo para construir la instancia
        public OurEventsDTO build() {
            return new OurEventsDTO(this);
        }
    }
}