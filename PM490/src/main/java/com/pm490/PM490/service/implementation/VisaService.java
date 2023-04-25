package com.pm490.PM490.service.implementation;


import com.pm490.PM490.model.VisaCard;
import com.pm490.PM490.repository.VisaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisaService {

    @Autowired
    private VisaRepository visaRepository;

    public VisaCard save(VisaCard visaCard) {
        return visaRepository.save(visaCard);
    }
}
