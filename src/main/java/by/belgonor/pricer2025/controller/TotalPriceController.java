package by.belgonor.pricer2025.controller;

import by.belgonor.pricer2025.entity.TotalPrice;
import by.belgonor.pricer2025.service.TotalPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/total-price")
public class TotalPriceController {

    @Autowired
    private TotalPriceService totalPriceService;

    @GetMapping
    public List<TotalPrice> getAllTotalPrices() {
        return totalPriceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TotalPrice> getTotalPriceById(@PathVariable Integer id) {
        TotalPrice totalPrice = totalPriceService.findById(id);
        if (totalPrice != null) {
            return ResponseEntity.ok(totalPrice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TotalPrice> createTotalPrice(@RequestBody TotalPrice totalPrice) {
        TotalPrice savedTotalPrice = totalPriceService.save(totalPrice);
        return ResponseEntity.ok(savedTotalPrice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TotalPrice> updateTotalPrice(@PathVariable Integer id, @RequestBody TotalPrice totalPrice) {
        TotalPrice existingTotalPrice = totalPriceService.findById(id);
        if (existingTotalPrice != null) {
            totalPrice.setId(id);
            TotalPrice updatedTotalPrice = totalPriceService.save(totalPrice);
            return ResponseEntity.ok(updatedTotalPrice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTotalPrice(@PathVariable Integer id) {
        totalPriceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
