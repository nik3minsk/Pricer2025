package by.belgonor.pricer2025.controller;


import by.belgonor.pricer2025.dto.SellerRequest;
import by.belgonor.pricer2025.entity.*;
import by.belgonor.pricer2025.repository.CatRepo;
import by.belgonor.pricer2025.repository.CurrencyRepo;
import by.belgonor.pricer2025.repository.RulesForBusinessRepo;
import by.belgonor.pricer2025.repository.SellerRepo;
import by.belgonor.pricer2025.service.SellerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MainController {
    @Autowired
    private CurrencyRepo currencyRepo;


    @Autowired
    private CatRepo catRepo;
    private ObjectMapper objectMapper;
    @Autowired
    private SellerRepo sellerRepo;
    @Autowired
    private RulesForBusinessRepo rulesForBusinessRepo;

    @GetMapping("/api/main")
    public String mainListener() {
        return "Hello World!";
    }

    @GetMapping("/api/cat")
    public String giveCatListener() {
        Cat cat = new Cat("Barsik", 5, 10);
        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(cat);
        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
            System.out.println("Error with cat class e = " + e);
        }
        return jsonData;
    }

    @PostMapping("/api/special")
    public String giveCatListenerPost(@RequestParam String name) {
        Cat cat = new Cat(name, 5, 10);
//        Cat cat1 = new Cat(name, 5, 10);

        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(cat);
        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
            System.out.println("Error with cat class e = " + e);
        }
        return jsonData;
    }

    @PostMapping("/api/currency")
    public String giveCurrencyValuePost(@RequestParam String value, String value1) {
        Cat cat = new Cat(value, 5, Integer.valueOf(value1));
//        Cat cat1 = new Cat(name, 5, 10);
        System.out.println("value = " + value);
        System.out.println("value1 = " + value1);

        String jsonData = null;
        try {
            jsonData = objectMapper.writeValueAsString(cat);
        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
            System.out.println("Error with cat class e = " + e);
        }
        return jsonData;
    }

//
//    @PostMapping("/api/log")
//    public String log(){
////        Cat cat1 = new Cat(name, 5, 10);
//
//        String jsonData = null;
//        try {
//            jsonData = objectMapper.writeValueAsString(cat);
//        } catch (JsonProcessingException e) {
////            throw new RuntimeException(e);
//            System.out.println("Error with cat class e = " + e);
//        }
//        return jsonData;
//    }

    @PostMapping("/api/nbcurrency")
    public String getNbCurrency(@RequestBody Map<String, String> payload) {
        String date = payload.get("date");
        System.out.println("Received date: " + date);
        return "Received date: " + date;
    }


    @PostMapping("/api/add")
    public void addCat(@RequestBody Cat cat) {
        log.info("New Row: " + catRepo.save(cat));
//        catRepo.save(cat);
    }

    @GetMapping("/api/all")
    public List<Cat> getAllCats() {
        return catRepo.findAll();
    }

    @GetMapping("/api/cat-manage")
    public Cat getCatById(@RequestParam int id) {
//        return catRepo.findById(id).get();
        return catRepo.findById(id).orElse(null);
    }

    @DeleteMapping("/api/cat-manage")
    public void deleteCatById(@RequestParam int id) {
        catRepo.deleteById(id);
    }

    @PutMapping("/api/cat-manage")
    public void updateCat(@RequestBody Cat cat) {
        if (!catRepo.existsById(cat.getId())) {
            catRepo.save(cat);
        }
        log.info("Updated row: " + catRepo.save(cat));
        return;
    }

//    @PostMapping("/api/addPrice")
//    public void savePrice(@RequestBody Document document){
////        log.info("New Row: " + catRepo.save(cat));
////        catRepo.save(cat);
//        log.info("Saving price: " + document);
//    }

//    @PostMapping("/api/addPrice2")
//    public void savePrice(@RequestBody String name) {
////        log.info("New Row: " + catRepo.save(cat));
////        catRepo.save(cat);
//        log.info("Saving price: " + name);
//    }

//    @PostMapping("/api/addPrice")
//    public ResponseEntity<String> savePrice(@RequestBody PriceRequest_del priceRequest) {
//        log.info("Saving price: " + priceRequest);
//        return ResponseEntity.ok("Price added successfully" + priceRequest);
//    }

    @Autowired
    private SellerService sellerService;


    @PostMapping("/api/addPrice2")
    public ResponseEntity<String> addPrice(@RequestBody SellerRequest sellerRequest) {
        // Логирование данных
        log.info("Saving price details: " + sellerRequest.getSellerDetails());
        log.info("Saving column details: " + sellerRequest.getRules_for_xlsx_columns());
        // Преобразование SellerRequest в Seller

//        код валюты
        Currency currency = new Currency();
        currency.setCurrencyCode(643);
        currency.setBaseCurrencyCode(933);
        currency.setDate(LocalDate.now());
        System.out.println("currency = " + currency);
        currencyRepo.save(currency);

//      Преобразуем бизнес-правила
        RulesForBusiness rulesForBusiness = new RulesForBusiness();
        rulesForBusiness.setCurrencyCode(currency);
//        rulesForBusiness.setCurrencyCoefficient(BigDecimal.valueOf(Double.parseDouble(sellerRequest.getSellerDetails().getCurrencyBankCoeff())));
        System.out.println("sellerRequest.getSellerDetails().getCurrencyBankCoeff() = " + sellerRequest.getSellerDetails().getCurrencyBankCoeff());
//        BigDecimal proba = new BigDecimal(sellerRequest.getSellerDetails().getCurrencyBankCoeff());
//        System.out.println("proba = " + proba);
        rulesForBusiness.setCurrencyCoefficient(new  BigDecimal(sellerRequest.getSellerDetails().getCurrencyBankCoeff()));
        rulesForBusiness.setDeliveryCoefficient(new BigDecimal(sellerRequest.getSellerDetails().getCoeffDeliveryCost()));
        rulesForBusiness.setVatPercentInPrice(new BigDecimal(sellerRequest.getSellerDetails().getVatRate()));
        rulesForBusiness.setMinOrderSum(new BigDecimal(sellerRequest.getSellerDetails().getMinOrderSum()));
        System.out.println("rulesForBusiness = " + rulesForBusiness);
        rulesForBusinessRepo.save(rulesForBusiness);

//        Правила ссылок по столбцам
        RulesForXlsx rulesForXlsx = new RulesForXlsx();



//      Ссылки продавца = бизнес правила+правила по столбцам
        Seller seller = new Seller();
        seller.setPriceName(sellerRequest.getSellerDetails().getPriceName());
        seller.setPathToPrice(sellerRequest.getSellerDetails().getPathToPrice());
        seller.setEconomicRules(rulesForBusiness);
        seller.setXlsPriceRules(rulesForXlsx);
        System.out.println("seller = " + seller);
//        sellerRepo.save(seller);
        // Убедитесь, что все необходимые поля заполняются
//        sellerService.saveSaler(seller);
//        System.out.println("sellerRequest = " + sellerRequest);

        return ResponseEntity.ok("Price added successfully!!!   " + sellerRequest);
    }

}
