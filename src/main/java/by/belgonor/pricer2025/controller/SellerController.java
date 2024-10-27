package by.belgonor.pricer2025.controller;

import by.belgonor.pricer2025.dto.SellerDTO;
import by.belgonor.pricer2025.entity.Seller;
import by.belgonor.pricer2025.repository.SellerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerRepo sellerRepo;

    @GetMapping
    public List<SellerDTO> getAllSellers() {
        List<Seller> sellers = sellerRepo.findAll();
        return sellers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private SellerDTO convertToDTO(Seller seller) {
        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setId(seller.getId());
        sellerDTO.setPriceName(seller.getPriceName());
        sellerDTO.setPathToPrice(seller.getPathToPrice());
        sellerDTO.setIsGeneralPrice(seller.getIsGeneralPrice());
        return sellerDTO;
    }
}
