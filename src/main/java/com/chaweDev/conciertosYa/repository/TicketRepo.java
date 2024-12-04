package com.chaweDev.conciertosYa.repository;

import com.chaweDev.conciertosYa.entity.OurTickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<OurTickets, Integer> {

}

// Principio KISS (Keep It Simple, Stupid):
// La interfaz usa solo JpaRepository, evitando agregar metodos innecesarios para mantenerlo simple y directo.

// Principio DRY (Don't Repeat Yourself):
// Aprovecha los metodos ya incluidos en JpaRepository, evitando reescribir funciones comunes como save, findById, etc.

// Principio YAGNI (You Aren't Gonna Need It):
// No se agregan funciones extra que no se necesiten ahora, como queries personalizadas. Solo lo basico para lo que se requiere actualmente.

//Cuando usas interfaces genéricas como JpaRepository, también aplicas
// LSP, ya que cualquier subclase de JpaRepository debe cumplir con los
// métodos base sin alterarlos.