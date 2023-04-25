package com.pm490.PM490.repository;

import com.pm490.PM490.model.PaymentMethod;
import com.pm490.PM490.model.VisaCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisaRepository extends JpaRepository<VisaCard, Long> {
}
