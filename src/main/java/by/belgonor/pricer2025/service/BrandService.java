package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.entity.Brand;
import by.belgonor.pricer2025.repository.BrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepo brandRepo;

    public List<Brand> findAll() {
        return brandRepo.findAll();
    }

    public Brand findById(Integer id) {
        Optional<Brand> result = brandRepo.findById(id);
        return result.orElse(null); // Вернет null, если объект не найден
    }

    public Brand save(Brand brand) {
        return brandRepo.save(brand);
    }

    public void deleteById(Integer id) {
        brandRepo.deleteById(id);
    }
}
