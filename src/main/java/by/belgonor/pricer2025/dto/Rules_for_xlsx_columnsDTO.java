package by.belgonor.pricer2025.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class Rules_for_xlsx_columnsDTO {
    private String brandColNumber;
    private String articleColNumber;
    private String productCategoryColNumber;
    private String productNameColNumber;
    private String priceColNumber;
    private String onStockColNumber;
    private String barcodeColNumber;
    private String tnvedColNumber;
    private String ownPriceColNumber;
    private String ownOnStockColNumber;
    private String ownReservedOnStockColNumber;
    private String ownFreeOnStockColNumber;
    private String ownPriceForSiteColNumber;
    private String headerRowNumber;
    private String startPriceRowNumber;

    public Rules_for_xlsx_columnsDTO() {
    }

    public Rules_for_xlsx_columnsDTO(String brandColNumber, String articleColNumber, String productCategoryColNumber, String productNameColNumber, String priceColNumber, String onStockColNumber, String barcodeColNumber, String tnvedColNumber, String ownPriceColNumber, String ownOnStockColNumber, String ownReservedOnStockColNumber, String ownFreeOnStockColNumber, String ownPriceForSiteColNumber, String headerRowNumber, String startPriceRowNumber) {
        this.brandColNumber = brandColNumber;
        this.articleColNumber = articleColNumber;
        this.productCategoryColNumber = productCategoryColNumber;
        this.productNameColNumber = productNameColNumber;
        this.priceColNumber = priceColNumber;
        this.onStockColNumber = onStockColNumber;
        this.barcodeColNumber = barcodeColNumber;
        this.tnvedColNumber = tnvedColNumber;
        this.ownPriceColNumber = ownPriceColNumber;
        this.ownOnStockColNumber = ownOnStockColNumber;
        this.ownReservedOnStockColNumber = ownReservedOnStockColNumber;
        this.ownFreeOnStockColNumber = ownFreeOnStockColNumber;
        this.ownPriceForSiteColNumber = ownPriceForSiteColNumber;
        this.headerRowNumber = headerRowNumber;
        this.startPriceRowNumber = startPriceRowNumber;
    }

    @Override
    public String toString() {
        return "Rules_for_xlsx_columnsDTO{" +
                "brandColNumber='" + brandColNumber + '\'' +
                ", articleColNumber='" + articleColNumber + '\'' +
                ", productCategoryColNumber='" + productCategoryColNumber + '\'' +
                ", productNameColNumber='" + productNameColNumber + '\'' +
                ", priceColNumber='" + priceColNumber + '\'' +
                ", onStockColNumber='" + onStockColNumber + '\'' +
                ", barcodeColNumber='" + barcodeColNumber + '\'' +
                ", tnvedColNumber='" + tnvedColNumber + '\'' +
                ", ownPriceColNumber='" + ownPriceColNumber + '\'' +
                ", ownOnStockColNumber='" + ownOnStockColNumber + '\'' +
                ", ownReservedOnStockColNumber='" + ownReservedOnStockColNumber + '\'' +
                ", ownFreeOnStockColNumber='" + ownFreeOnStockColNumber + '\'' +
                ", ownPriceForSiteColNumber='" + ownPriceForSiteColNumber + '\'' +
                ", headerRowNumber=" + headerRowNumber +
                ", startPriceRowNumber=" + startPriceRowNumber +
                '}';
    }
}
