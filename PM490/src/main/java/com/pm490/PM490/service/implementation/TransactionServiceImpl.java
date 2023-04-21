
package com.pm490.PM490.service.implementation;

import com.pm490.PM490.model.Role;
import com.pm490.PM490.repository.TransactionRepository;
import com.pm490.PM490.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Double getTotalRecAmountByUserRole(String userRole) {
        Double total = transactionRepository.getTotalRecAmountByUserRole(userRole);
        System.out.println("------------------------------"+total+"------------");
        return total;
    }

}
