package by.belgonor.pricer2025.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class SellerDetails {
    private String priceName;
    private String pathToPrice;
    private String currencyCode;
    private String vatRate;
    private String currencyBankCoeff;
    private String coeffDeliveryCost;
    private String minOrderSum;
    private String isGeneralPrice;


    public SellerDetails() {
    }

    public SellerDetails(String priceName, String pathToPrice, String currencyCode, String vatRate, String currencyBankCoeff, String coeffDeliveryCost, String minOrderSum, String isGeneralPrice) {
        this.priceName = priceName;
        this.pathToPrice = pathToPrice;
        this.currencyCode = currencyCode;
        this.vatRate = vatRate;
        this.currencyBankCoeff = currencyBankCoeff;
        this.coeffDeliveryCost = coeffDeliveryCost;
        this.minOrderSum = minOrderSum;
        this.isGeneralPrice = isGeneralPrice;
    }

    @Override
    public String toString() {
        return "SellerDetails{" +
                "priceName='" + priceName + '\'' +
                ", pathToPrice='" + pathToPrice + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", vatRate='" + vatRate + '\'' +
                ", currencyBankCoeff='" + currencyBankCoeff + '\'' +
                ", coeffDeliveryCost='" + coeffDeliveryCost + '\'' +
                ", minOrderSum='" + minOrderSum + '\'' +
                ", isGeneralPrice='" + isGeneralPrice + '\'' +
                '}';
    }
}
