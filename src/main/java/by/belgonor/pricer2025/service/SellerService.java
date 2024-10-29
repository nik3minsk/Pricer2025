// src/main/java/by/belgonor/pricer2025/service/SalerService.java
package by.belgonor.pricer2025.service;

import by.belgonor.pricer2025.dto.SellerRequest;
import by.belgonor.pricer2025.entity.CurrencyNb;
import by.belgonor.pricer2025.entity.RulesForBusiness;
import by.belgonor.pricer2025.entity.RulesForXlsx;
import by.belgonor.pricer2025.entity.Seller;
import by.belgonor.pricer2025.repository.CurrencyNbRepo;
import by.belgonor.pricer2025.repository.SellerRepo;
import org.apache.tomcat.util.digester.Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SellerService {

    @Autowired
    private SellerRepo sellerRepo;


    public void addSeller(Seller seller) {
        sellerRepo.save(seller);
    }


//    заполнение полей бизнес-правил поставщика, если поля пустые, присваиваем значения по умолчанию = 1
    public static void setRulesForBusiness(RulesForBusiness rulesForBusiness, SellerRequest sellerRequest) {

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
    }

    //        заполнение полей с номерами столбцов для парсинга

    public static void setRulesForXlsx(RulesForXlsx rulesForXlsx, SellerRequest sellerRequest) {


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

        rulesForXlsx.setColumnReservedOnStockOwn(
                sellerRequest.getRules_for_xlsx_columns().getOwnReservedOnStockColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getOwnReservedOnStockColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnReservedOnStockColNumber())
                        : 0);

        rulesForXlsx.setColumnPriceForSiteOwn(
                sellerRequest.getRules_for_xlsx_columns().getOwnPriceForSiteColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getOwnPriceForSiteColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getOwnPriceForSiteColNumber())
                        : 0);

        rulesForXlsx.setColumnProductName(
                sellerRequest.getRules_for_xlsx_columns().getProductNameColNumber() != null && !sellerRequest.getRules_for_xlsx_columns().getProductNameColNumber().isEmpty()
                        ? Integer.parseInt(sellerRequest.getRules_for_xlsx_columns().getProductNameColNumber())
                        : 0);



    }

}
