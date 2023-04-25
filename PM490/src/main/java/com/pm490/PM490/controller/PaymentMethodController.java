package com.pm490.PM490.controller;

import com.pm490.PM490.model.PaymentMethod;
import com.pm490.PM490.model.User;
import com.pm490.PM490.repository.PaymentMethodRepository;
import com.pm490.PM490.repository.UserRepository;
import com.pm490.PM490.service.CurrentUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paymentmethod")
@PreAuthorize("hasAnyAuthority('CUSTOMER', 'VENDOR')")
public class PaymentMethodController {
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CurrentUserService currentUserService;

    @GetMapping("/index")
    List<PaymentMethod> index() {
        Long userId = currentUserService.findLoggedUserId();
        return paymentMethodRepository.findAllByUser(userId);
    }

    @PostMapping("/create")
    PaymentMethod create(@RequestBody PaymentMethod newPaymentMethod) {
        User user = currentUserService.findLoggedUser();
        newPaymentMethod.setUser(user);
        return paymentMethodRepository.save(newPaymentMethod);
    }

    @PutMapping("/update/{id}")
    PaymentMethod update(@RequestBody PaymentMethod paymentMethod, @PathVariable Long id) {
        User user = currentUserService.findLoggedUser();
        return paymentMethodRepository.findById(id)
                .map(pm -> {
                    pm.setCvv(paymentMethod.getCvv());
                    pm.setNumber(paymentMethod.getNumber());
                    pm.setZipcode(paymentMethod.getZipcode());
                    pm.setFullname(paymentMethod.getFullname());
                    pm.setType(paymentMethod.getType());
                    return paymentMethodRepository.save(pm);
                })
                .orElseGet(() -> {
                    paymentMethod.setUser(user);
                    return paymentMethodRepository.save(paymentMethod);
                });
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) {
        paymentMethodRepository.deleteById(id);
    }
}

