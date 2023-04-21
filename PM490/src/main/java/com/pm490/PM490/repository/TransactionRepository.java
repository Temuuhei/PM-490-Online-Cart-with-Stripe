package com.pm490.PM490.repository;

import com.pm490.PM490.model.Role;
import com.pm490.PM490.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    //@Query("select sum(t.amount) from Transaction t where t.user.role = :userRole")
    @Query(value = "select sum(t.amount) from Transaction t join User u on t.user_id = u.id where u.role=:userRole", nativeQuery = true)
    Double getTotalRecAmountByUserRole(@Param("userRole") String userRole);


}
