package by.belgonor.pricer2025.repository;

import by.belgonor.pricer2025.entity.CurrencyNb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyNbRepo extends JpaRepository<CurrencyNb, Integer> {
}
