package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.dto.CurrencyNbDTO;
import by.belgonor.pricer2025.entity.CurrencyNb;
import by.belgonor.pricer2025.entity.CurrencyRate;
import by.belgonor.pricer2025.repository.CurrencyNbRepo;
import by.belgonor.pricer2025.converter.CurrencyNbConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyNbService {
    @Autowired
    private CurrencyNbRepo currencyNbRepo;

//    public List<CurrencyNbDTO> getAllCurrencyNbs() {
//        List<CurrencyNb> currencyNbs = currencyNbRepo.findAll();
//        return currencyNbs.stream()
//                .map(CurrencyNbConverter::toDTO)
//                .collect(Collectors.toList());
//    }

    public List<CurrencyNbDTO> getAllCurrencyNbs() {
        List<CurrencyNb> currencyNbs = currencyNbRepo.findAll();
        return currencyNbs.stream()
                .map(currencyNb -> new CurrencyNbDTO(currencyNb.getDate(), currencyNb.getCurrencyCode(), currencyNb.getBaseCurrencyCode(), currencyNb.getCurrencyName()))
                .collect(Collectors.toList());
    }

    public CurrencyNbDTO getCurrencyNbById(Integer id) {
        CurrencyNb currencyNb = currencyNbRepo.findById(id).orElseThrow(() -> new RuntimeException("CurrencyNb not found"));
        return CurrencyNbConverter.toDTO(currencyNb);
    }

    public void saveCurrencyNb(CurrencyNbDTO currencyNbDTO) {
        CurrencyNb currencyNb = CurrencyNbConverter.toEntity(currencyNbDTO);
        currencyNbRepo.save(currencyNb);
    }



}
