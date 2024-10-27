package by.belgonor.pricer2025.controller;

import by.belgonor.pricer2025.dto.SellerRequest;
import by.belgonor.pricer2025.entity.*;
import by.belgonor.pricer2025.repository.*;
import by.belgonor.pricer2025.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PriceController {

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

    @PostMapping("/api/addPrice2")
    public ResponseEntity<String> addPrice(@RequestBody SellerRequest sellerRequest) {
        log.info("Saving price details: " + sellerRequest.getSellerDetails());
        log.info("Saving column details: " + sellerRequest.getRules_for_xlsx_columns());

        // поиск кода валюты по id для записи в бизнес-правила поставщика
        CurrencyNb currency = new CurrencyNb();
        Integer currencyId = Integer.parseInt(sellerRequest.getSellerDetails().getCurrencyCode());
        currency = currencyNbRepo.findById(currencyId).get();
        System.out.println("currency  sout= " + currency);

        // заполнение остальных полей бизнес-правил поставщика, если поля пустые, присваиваем значения по умолчанию = 1
        RulesForBusiness rulesForBusiness = new RulesForBusiness();
        rulesForBusiness.setCurrencyCode(currency);
        rulesForBusiness.setCurrencyCoefficient(
                sellerRequest.getSellerDetails().getCurrencyBankCoeff() == null || sellerRequest.getSellerDetails().getCurrencyBankCoeff().isEmpty()
                        ? BigDecimal.valueOf(1)
                        : new BigDecimal(sellerRequest.getSellerDetails().getCurrencyBankCoeff())
        );
        rulesForBusiness.setDeliveryCoefficient(
                sellerRequest.getSellerDetails().getCoeffDeliveryCost() == null || sellerRequest.getSellerDetails().getCoeffDeliveryCost().isEmpty()
                ? BigDecimal.valueOf(1)
                : new BigDecimal(sellerRequest.getSellerDetails().getCoeffDeliveryCost())
        );
        rulesForBusiness.setVatPercentInPrice(
                sellerRequest.getSellerDetails().getVatRate() == null || sellerRequest.getSellerDetails().getVatRate().isEmpty()
                        ? BigDecimal.valueOf(1)
                        : new BigDecimal(sellerRequest.getSellerDetails().getVatRate())
        );
        rulesForBusiness.setMinOrderSum(
                sellerRequest.getSellerDetails().getMinOrderSum() == null || sellerRequest.getSellerDetails().getMinOrderSum().isEmpty()
                        ? BigDecimal.valueOf(1)
                        : new BigDecimal(sellerRequest.getSellerDetails().getMinOrderSum())
        );
        System.out.println("rulesForBusiness = " + rulesForBusiness);

//        сохраняем бизнес-правила для нового поставщика
        rulesForBusinessRepo.save(rulesForBusiness);

//        ВРЕМЕННОЕ!!!!  задание значения ссылки на Заполненные ЗНАЧЕНИЯ ШАПКИ
        XlsxHeaderValue xlsxHeaderValue = new XlsxHeaderValue();
        Integer numberForXlsxHeader = 1;
        Optional<XlsxHeaderValue> optionalXlsxHeaderValue = xlsxHeaderValueRepo.findById(numberForXlsxHeader);
        if (optionalXlsxHeaderValue.isPresent()) {
            xlsxHeaderValue = optionalXlsxHeaderValue.get();
        } else {
            log.warn("XlsxHeaderValue with id 1 not found");
        }

        log.info("xlsxHeaderValue: " + xlsxHeaderValue);
        log.info("xlsxHeaderValueRepo.findById(1): " + xlsxHeaderValueRepo.findById(1));
        System.out.println("xlsxHeaderValue = " + xlsxHeaderValue);
        System.out.println("xlsxHeaderValueRepo.findById(1) = " + xlsxHeaderValueRepo.findById(1));

//        сохраняем поля с номерами столбцов для парсинга
        RulesForXlsx rulesForXlsx = new RulesForXlsx();
        rulesForXlsx.setHeaderValues(xlsxHeaderValue);
//        rulesForXlsx.setHeaderStringNumber(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getHeaderRowNumber()));
//        rulesForXlsx.setStartPriceDataRowNumber(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getStartPriceRowNumber()));
//        rulesForXlsx.setColumnBrand(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getBrandColNumber()));
//        rulesForXlsx.setColumnArticle(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getArticleColNumber()));
//        rulesForXlsx.setColumnProductCategory(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getProductCategoryColNumber()));
//        rulesForXlsx.setColumnPrice(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getPriceColNumber()));
//        rulesForXlsx.setColumnOnStock(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOnStockColNumber()));
//        rulesForXlsx.setColumnTnved(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getTnvedColNumber()));
//        rulesForXlsx.setColumnBarcode(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getBarcodeColNumber()));
//        rulesForXlsx.setColumnPriceOnStockOwn(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnPriceColNumber()));
//        rulesForXlsx.setColumnOnStockOwn(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnOnStockColNumber()));
//        rulesForXlsx.setColumnFreeOnStock(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnFreeOnStockColNumber()));
//        rulesForXlsx.setColumnPriceForSiteOwn(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnPriceForSiteColNumber()));
//        rulesForXlsx.setColumnProductName(Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getProductNameColNumber()));

        rulesForXlsx.setHeaderStringNumber(
                sellerRequest.getRules_for_xlsx_columns().getHeaderRowNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getHeaderRowNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getHeaderRowNumber())
                        : 0);

        rulesForXlsx.setStartPriceDataRowNumber(
                sellerRequest.getRules_for_xlsx_columns().getStartPriceRowNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getStartPriceRowNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getStartPriceRowNumber())
                        : 0);

        rulesForXlsx.setColumnBrand(
                sellerRequest.getRules_for_xlsx_columns().getBrandColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getBrandColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getBrandColNumber())
                        : 0);

        rulesForXlsx.setColumnArticle(
                sellerRequest.getRules_for_xlsx_columns().getArticleColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getArticleColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getArticleColNumber())
                        : 0);

        rulesForXlsx.setColumnProductCategory(
                sellerRequest.getRules_for_xlsx_columns().getProductCategoryColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getProductCategoryColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getProductCategoryColNumber())
                        : 0);

        rulesForXlsx.setColumnPrice(
                sellerRequest.getRules_for_xlsx_columns().getPriceColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getPriceColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getPriceColNumber())
                        : 0);

        rulesForXlsx.setColumnOnStock(
                sellerRequest.getRules_for_xlsx_columns().getOnStockColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getOnStockColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOnStockColNumber())
                        : 0);

        rulesForXlsx.setColumnTnved(
                sellerRequest.getRules_for_xlsx_columns().getTnvedColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getTnvedColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getTnvedColNumber())
                        : 0);

        rulesForXlsx.setColumnBarcode(
                sellerRequest.getRules_for_xlsx_columns().getBarcodeColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getBarcodeColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getBarcodeColNumber())
                        : 0);

        rulesForXlsx.setColumnPriceOnStockOwn(
                sellerRequest.getRules_for_xlsx_columns().getOwnPriceColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getOwnPriceColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnPriceColNumber())
                        : 0);

        rulesForXlsx.setColumnOnStockOwn(
                sellerRequest.getRules_for_xlsx_columns().getOwnOnStockColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getOwnOnStockColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnOnStockColNumber())
                        : 0);

        rulesForXlsx.setColumnFreeOnStock(
                sellerRequest.getRules_for_xlsx_columns().getOwnFreeOnStockColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getOwnFreeOnStockColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnFreeOnStockColNumber())
                        : 0);

        rulesForXlsx.setColumnPriceForSiteOwn(
                sellerRequest.getRules_for_xlsx_columns().getOwnPriceForSiteColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getOwnPriceForSiteColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnPriceForSiteColNumber())
                        : 0);

        rulesForXlsx.setColumnProductName(
                sellerRequest.getRules_for_xlsx_columns().getProductNameColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getProductNameColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getProductNameColNumber())
                        : 0);
        rulesForXlsxRepo.save(rulesForXlsx);


        // Сохраняем все поля нового поставщика, в т.ч. и ссылки на валюту, бизнес-правила, xlsx правила

        Seller seller = new Seller();
        seller.setPriceName(sellerRequest.getSellerDetails().getPriceName());
        seller.setPathToPrice(sellerRequest.getSellerDetails().getPathToPrice());
        seller.setEconomicRules(rulesForBusiness);
        seller.setXlsPriceRules(rulesForXlsx);
        sellerRepo.save(seller);

//        return ResponseEntity.ok("Price added successfully!!!   " + sellerRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Price added successfully");
        response.put("sellerRequest", sellerRequest);

//        return ResponseEntity.ok(response);
        return ResponseEntity.ok("Price added successfully!!!   " + sellerRequest);
    }


    @DeleteMapping("/api/sellers/{id}")
    public ResponseEntity<String> deleteSeller(@PathVariable Integer id) {
        try {
            sellerRepo.deleteById(id);
            return ResponseEntity.ok("Прайс успешно удален");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при удалении прайса");
        }
    }



}
