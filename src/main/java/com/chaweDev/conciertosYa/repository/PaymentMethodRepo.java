package com.chaweDev.conciertosYa.repository;

import com.chaweDev.conciertosYa.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Integer> {

}
