package com.pm490.PM490.service.implementation;

import com.pm490.PM490.model.MasterCard;
import com.pm490.PM490.repository.MasterCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaserCardService {

    @Autowired
    private MasterCardRepository masterCardRepository;

    public MasterCard save(MasterCard masterCard) {
        return masterCardRepository.save(masterCard);
    }
}
