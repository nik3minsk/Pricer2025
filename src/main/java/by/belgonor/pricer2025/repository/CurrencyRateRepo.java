package by.belgonor.pricer2025.repository;

import by.belgonor.pricer2025.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyRateRepo extends JpaRepository<CurrencyRate, Integer> {
    List<CurrencyRate> findByDate(LocalDate date);
}
