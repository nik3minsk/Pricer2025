// CurrencyNbController.java
package by.belgonor.pricer2025.controller;

import by.belgonor.pricer2025.entity.CurrencyNb;
import by.belgonor.pricer2025.repository.CurrencyNbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CurrencyNbController {

    @Autowired
    private CurrencyNbRepo currencyNbRepo;

    @GetMapping("/currencylist")
    public List<CurrencyNb> getCurrencyList() {
        System.out.println("currencyNbRepo = " + currencyNbRepo);
        return currencyNbRepo.findAll();

    }


}
