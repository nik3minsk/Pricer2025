package by.belgonor.pricer2025.controller;

import by.belgonor.pricer2025.dto.SellerDTO;
import by.belgonor.pricer2025.dto.SellerRequest;
import by.belgonor.pricer2025.entity.*;
import by.belgonor.pricer2025.repository.*;
import by.belgonor.pricer2025.service.SellerService;
import by.belgonor.pricer2025.service.XlsxParse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.belgonor.pricer2025.entity.Seller;
import by.belgonor.pricer2025.repository.SellerRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SellerController {

    @Autowired
    private CurrencyNbRepo currencyNbRepo;
    @Autowired
    private RulesForBusinessRepo rulesForBusinessRepo;
    @Autowired
    private SellerRepo sellerRepo;
//    @Autowired
//    private SellerService sellerService;
    @Autowired
    private RulesForXlsxRepo rulesForXlsxRepo;
    @Autowired
    private XlsxHeaderValueRepo xlsxHeaderValueRepo;

//    @PostMapping("/api/addPrice2")
    @PostMapping("/api/addSeller")
    public ResponseEntity<String> addPrice(@RequestBody SellerRequest sellerRequest) {
        log.info("Saving price details: " + sellerRequest.getSellerDetails());
        log.info("Saving column details: " + sellerRequest.getRules_for_xlsx_columns());

//        Создание блока бизнес-правил для продавца
        RulesForBusiness rulesForBusiness = new RulesForBusiness();
        // поиск кода валюты по id для записи в бизнес-правила поставщика
        Integer currencyId = Integer.parseInt(sellerRequest.getSellerDetails().getCurrencyCode());
        rulesForBusiness.setCurrencyCode(currencyNbRepo.findById(currencyId).get());

        // заполнение остальных полей бизнес-правил поставщика, если поля пустые, присваиваем значения по умолчанию = 1
        SellerService.setRulesForBusiness(rulesForBusiness, sellerRequest);
//        сохраняем бизнес-правила для нового поставщика
        rulesForBusinessRepo.save(rulesForBusiness);

//        сохраняем поля с номерами столбцов для парсинга
        RulesForXlsx rulesForXlsx = new RulesForXlsx();
        SellerService.setRulesForXlsx(rulesForXlsx, sellerRequest);

//        готовим данные для парсинга значений шапки прайса.
        XlsxHeaderValue xlsxHeaderValue = new XlsxHeaderValue();
        String fileToRead = sellerRequest.getSellerDetails().getPathToPrice();
        Integer headerStringNumber = rulesForXlsx.getHeaderStringNumber();
//        вызываем метод парсинга значений заголовков таблицы прайса
        XlsxParse.parseXlsxHeader(fileToRead, headerStringNumber, rulesForXlsx, xlsxHeaderValue);

        xlsxHeaderValueRepo.save(xlsxHeaderValue);


        log.info("xlsxHeaderValue: " + xlsxHeaderValue);
//        log.info("xlsxHeaderValueRepo.findById(1): " + xlsxHeaderValueRepo.findById(1));
//        System.out.println("xlsxHeaderValue = " + xlsxHeaderValue);
//        System.out.println("xlsxHeaderValueRepo.findById(1) = " + xlsxHeaderValueRepo.findById(1));

//      заполняем значения
        rulesForXlsx.setHeaderValues(xlsxHeaderValue);
        rulesForXlsxRepo.save(rulesForXlsx);


        // Сохраняем все поля нового поставщика, в т.ч. и ссылки на валюту, бизнес-правила, xlsx правила

        Seller seller = new Seller();
        seller.setPriceName(sellerRequest.getSellerDetails().getPriceName());
        seller.setPathToPrice(sellerRequest.getSellerDetails().getPathToPrice());
        seller.setEconomicRules(rulesForBusiness);
        seller.setXlsPriceRules(rulesForXlsx);
        seller.setIsGeneralPrice(Boolean.valueOf(sellerRequest.getSellerDetails().getIsGeneralPrice()));
        sellerRepo.save(seller);

//        return ResponseEntity.ok("Price added successfully!!!   " + sellerRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Price added successfully");
        response.put("sellerRequest", sellerRequest);

//        return ResponseEntity.ok(response);
        return ResponseEntity.ok("Price added successfully!!!   " + sellerRequest);
    }

//          #######################################################

    @DeleteMapping("/api/sellers/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable Integer id) {
        try {
            sellerRepo.deleteById(id);
            return ResponseEntity.ok("Прайс успешно удален");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при удалении прайса");
        }
    }

//
//    @Autowired
//    private SellerRepo sellerRepo;

    @GetMapping("/api/sellers")
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
