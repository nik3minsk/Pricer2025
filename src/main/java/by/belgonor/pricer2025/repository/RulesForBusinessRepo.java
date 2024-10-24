package by.belgonor.pricer2025.repository;

import by.belgonor.pricer2025.entity.RulesForBusiness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RulesForBusinessRepo extends JpaRepository<RulesForBusiness, Integer> {
}
