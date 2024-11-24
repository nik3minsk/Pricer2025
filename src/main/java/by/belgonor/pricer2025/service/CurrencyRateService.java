package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.entity.CurrencyRate;
import by.belgonor.pricer2025.repository.CurrencyRateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyRateService {
    @Autowired
    private CurrencyRateRepo currencyRateRepo;

    public Optional<CurrencyRate> getCurrencyRateByDateAndCurrencyId(LocalDate date, Integer currencyId) {
        return currencyRateRepo.findByDateAndCurrencyId(date, currencyId);
    }

    public List<CurrencyRate> getCurrencyRatesByDate(LocalDate date) { return currencyRateRepo.findByDate(date); }

}
