package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.entity.Seller;
import by.belgonor.pricer2025.entity.TotalPrice;
import by.belgonor.pricer2025.repository.TotalPriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TotalPriceService {

    @Autowired
    private TotalPriceRepo totalPriceRepo;

    public List<TotalPrice> findAll() {
        return totalPriceRepo.findAll();
    }

    public TotalPrice findById(Integer id) {
        Optional<TotalPrice> result = totalPriceRepo.findById(id);
        return result.orElse(null); // Вернет null, если объект не найден
    }

    public TotalPrice save(TotalPrice totalPrice) {
        return totalPriceRepo.save(totalPrice);
    }

    public void deleteById(Integer id) {
        totalPriceRepo.deleteById(id);
    }

    public List<TotalPrice> getTotalPricesByDate(LocalDate date) { return totalPriceRepo.findByDate(date); }

    public List<TotalPrice> getTotalPricesByDateAndIdSaler(LocalDate date, Seller idSaler) { return totalPriceRepo.findByDateAndIdSaler(date, idSaler); }
}
