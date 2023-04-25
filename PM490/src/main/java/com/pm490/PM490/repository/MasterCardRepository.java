package com.pm490.PM490.repository;

import com.pm490.PM490.model.MasterCard;
import com.pm490.PM490.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterCardRepository extends JpaRepository<MasterCard, Long> {
}
