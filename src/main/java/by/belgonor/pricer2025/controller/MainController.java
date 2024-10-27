package by.belgonor.pricer2025.controller;


import by.belgonor.pricer2025.dto.SellerRequest;
import by.belgonor.pricer2025.entity.*;
import by.belgonor.pricer2025.repository.CatRepo;
import by.belgonor.pricer2025.repository.CurrencyNbRepo;
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
import java.util.List;


@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class MainController {
    @Autowired
    private CurrencyNbRepo currencyNbRepo;


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
        TopCat cat = new TopCat("Barsik", 5, 10);
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
        TopCat cat = new TopCat(name, 5, 10);
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
        TopCat cat = new TopCat(value, 5, Integer.valueOf(value1));
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

//    @GetMapping("/api/nbcurrency")
//    public String getNbCurrency(@RequestBody Map<String, String> payload) {
//        String date = payload.get("date");
//        System.out.println("Received date: " + date);
//        return date;
//    }

//    @GetMapping("/currencylist")
//    public List<Currency> getCurrencyList() {
//        return currencyRepo.findAll();
//    }


    @PostMapping("/api/add")
    public void addCat(@RequestBody TopCat cat) {
        log.info("New Row: " + catRepo.save(cat));
//        catRepo.save(cat);
    }

    @GetMapping("/api/all")
    public List<TopCat> getAllCats() {
        return catRepo.findAll();
    }

    @GetMapping("/api/cat-manage")
    public TopCat getCatById(@RequestParam int id) {
//        return catRepo.findById(id).get();
        return catRepo.findById(id).orElse(null);
    }

    @DeleteMapping("/api/cat-manage")
    public void deleteCatById(@RequestParam int id) {
        catRepo.deleteById(id);
    }

    @PutMapping("/api/cat-manage")
    public void updateCat(@RequestBody TopCat cat) {
        if (!catRepo.existsById(cat.getId())) {
            catRepo.save(cat);
        }
        log.info("Updated row: " + catRepo.save(cat));
        return;
    }

//    @GetMapping("/api/sellers")
//    public List<Seller> getAllSellers() {
//        return sellerRepo.findAll();
//    }

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
//
//    @Autowired
//    private SellerService sellerService;
//
//
//    @PostMapping("/api/addPrice2")
//    public ResponseEntity<String> addPrice(@RequestBody SellerRequest sellerRequest) {
//        // Логирование данных
//        log.info("Saving price details: " + sellerRequest.getSellerDetails());
//        log.info("Saving column details: " + sellerRequest.getRules_for_xlsx_columns());
//        // Преобразование SellerRequest в Seller
//
////        код валюты
//        CurrencyNb currency = new CurrencyNb();
////        currency.setCurrencyCode(643);
////        currency.setBaseCurrencyCode(933);
////        currency.setDate(LocalDate.now());
////        System.out.println("currency = " + currency);
//        currencyNbRepo.save(currency);
//
////      Преобразуем бизнес-правила
//        RulesForBusiness rulesForBusiness = new RulesForBusiness();
//        rulesForBusiness.setCurrencyCode(currency);
////        rulesForBusiness.setCurrencyCoefficient(BigDecimal.valueOf(Double.parseDouble(sellerRequest.getSellerDetails().getCurrencyBankCoeff())));
//        System.out.println("sellerRequest.getSellerDetails().getCurrencyBankCoeff() = " + sellerRequest.getSellerDetails().getCurrencyBankCoeff());
////        BigDecimal proba = new BigDecimal(sellerRequest.getSellerDetails().getCurrencyBankCoeff());
////        System.out.println("proba = " + proba);
//        rulesForBusiness.setCurrencyCoefficient(new  BigDecimal(sellerRequest.getSellerDetails().getCurrencyBankCoeff()));
//        rulesForBusiness.setDeliveryCoefficient(new BigDecimal(sellerRequest.getSellerDetails().getCoeffDeliveryCost()));
//        rulesForBusiness.setVatPercentInPrice(new BigDecimal(sellerRequest.getSellerDetails().getVatRate()));
//        rulesForBusiness.setMinOrderSum(new BigDecimal(sellerRequest.getSellerDetails().getMinOrderSum()));
//        System.out.println("rulesForBusiness = " + rulesForBusiness);
//        rulesForBusinessRepo.save(rulesForBusiness);
//
////        Правила ссылок по столбцам
//        RulesForXlsx rulesForXlsx = new RulesForXlsx();
//
//
//
////      Ссылки продавца = бизнес правила+правила по столбцам
//        Seller seller = new Seller();
//        seller.setPriceName(sellerRequest.getSellerDetails().getPriceName());
//        seller.setPathToPrice(sellerRequest.getSellerDetails().getPathToPrice());
//        seller.setEconomicRules(rulesForBusiness);
//        seller.setXlsPriceRules(rulesForXlsx);
//        System.out.println("seller = " + seller);
////        sellerRepo.save(seller);
//        // Убедитесь, что все необходимые поля заполняются
////        sellerService.saveSaler(seller);
////        System.out.println("sellerRequest = " + sellerRequest);
//
//        return ResponseEntity.ok("Price added successfully!!!   " + sellerRequest);
//    }

}
