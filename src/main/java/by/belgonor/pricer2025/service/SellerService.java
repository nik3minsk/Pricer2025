// src/main/java/by/belgonor/pricer2025/service/SalerService.java
package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.entity.Seller;
import by.belgonor.pricer2025.repository.SellerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    private SellerRepo sellerRepo;

    public void addSeller(Seller seller) {
        sellerRepo.save(seller);
    }



}
