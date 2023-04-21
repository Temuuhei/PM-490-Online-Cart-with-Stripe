package com.pm490.PM490.repository;

import com.pm490.PM490.model.PaymentMethod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    @Query(value = "SELECT * FROM PaymentMethod p WHERE p.user_id = :ui", nativeQuery = true)
    List<PaymentMethod> findAllByUser(@Param("ui") long ui);

}
