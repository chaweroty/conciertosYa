package com.chaweDev.conciertosYa.repository;

import com.chaweDev.conciertosYa.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice, Integer> {

}
