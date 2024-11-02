package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.entity.XlsxHeaderValue;
import by.belgonor.pricer2025.repository.XlsxHeaderValueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class XlsxHeaderValueService {

    @Autowired
    private XlsxHeaderValueRepo xlsxHeaderValueRepo;

    public XlsxHeaderValue findById(Integer id) {
        Optional<XlsxHeaderValue> result = xlsxHeaderValueRepo.findById(id);
        return result.orElse(null); // Вернет null, если объект не найден
    }
}
