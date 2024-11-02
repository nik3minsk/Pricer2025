package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.entity.RulesForXlsx;
import by.belgonor.pricer2025.repository.RulesForXlsxRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RulesForXlsxService {

    @Autowired
    private RulesForXlsxRepo rulesForXlsxRepo;

    public RulesForXlsx findByIdDirectly(Integer id) {
        Optional<RulesForXlsx> result = rulesForXlsxRepo.findById(id);
        return result.orElse(null); // Вернет null, если объект не найден
    }
}
