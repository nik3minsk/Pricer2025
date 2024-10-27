package by.belgonor.pricer2025.repository;

import by.belgonor.pricer2025.entity.XlsxHeaderValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XlsxHeaderValueRepo extends JpaRepository<XlsxHeaderValue, Integer> {
}
