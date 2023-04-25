package com.pm490.PM490.controller;

import com.pm490.PM490.model.MasterCard;
import com.pm490.PM490.model.PaymentMethod;
import com.pm490.PM490.model.User;
import com.pm490.PM490.model.VisaCard;
import com.pm490.PM490.service.CurrentUserService;
import com.pm490.PM490.service.implementation.MaserCardService;
import com.pm490.PM490.service.implementation.VisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paymentmethod/card")
@PreAuthorize("hasAnyAuthority('CUSTOMER', 'VENDOR')")
public class CardController {

    @Autowired
    VisaService visaService;

    @Autowired
    MaserCardService maserCardService;

    @Autowired
    CurrentUserService currentUserService;

    @PostMapping("/visa")
    VisaCard createVisaCard(@RequestBody VisaCard visaCard) {
        User user = currentUserService.findLoggedUser();
        visaCard.setUser(user);
        return visaService.save(visaCard);
    }

    @PostMapping("/mastercard")
    MasterCard createMasterCard(@RequestBody MasterCard masterCard) {
        User user = currentUserService.findLoggedUser();
        masterCard.setUser(user);
        return maserCardService.save(masterCard);
    }

}
