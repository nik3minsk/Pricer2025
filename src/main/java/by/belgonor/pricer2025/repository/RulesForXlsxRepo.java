package by.belgonor.pricer2025.repository;

import by.belgonor.pricer2025.entity.RulesForXlsx;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RulesForXlsxRepo extends CrudRepository<RulesForXlsx, Integer> {
    Optional<Object> findById(RulesForXlsx xlsPriceRules);
}
