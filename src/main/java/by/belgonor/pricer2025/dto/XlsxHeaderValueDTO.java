package by.belgonor.pricer2025.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class XlsxHeaderValueDTO {
    private Integer id;
    private String columnBrand;
    private String columnArticle;
    private String columnProductCategory;
    private String columnPrice;
    private String columnOnStock;
    private String columnTnved;
    private String columnBarcode;
    private String columnPriceOnStockOwn;
    private String columnOnStockOwn;
    private String columnReservedOnStockOwn;
    private String columnFreeOnStock;
    private String columnPriceForSiteOwn;
    private String columnProductName;

    public XlsxHeaderValueDTO() {
    }

    public XlsxHeaderValueDTO(Integer id, String columnBrand, String columnArticle, String columnProductCategory, String columnPrice, String columnOnStock, String columnTnved, String columnBarcode, String columnPriceOnStockOwn, String columnOnStockOwn, String columnReservedOnStockOwn, String columnFreeOnStock, String columnPriceForSiteOwn, String columnProductName) {
        this.id = id;
        this.columnBrand = columnBrand;
        this.columnArticle = columnArticle;
        this.columnProductCategory = columnProductCategory;
        this.columnPrice = columnPrice;
        this.columnOnStock = columnOnStock;
        this.columnTnved = columnTnved;
        this.columnBarcode = columnBarcode;
        this.columnPriceOnStockOwn = columnPriceOnStockOwn;
        this.columnOnStockOwn = columnOnStockOwn;
        this.columnReservedOnStockOwn = columnReservedOnStockOwn;
        this.columnFreeOnStock = columnFreeOnStock;
        this.columnPriceForSiteOwn = columnPriceForSiteOwn;
        this.columnProductName = columnProductName;
    }

    @Override
    public String toString() {
        return "XlsxHeaderValueDTO{" +
                "id=" + id +
                ", columnBrand='" + columnBrand + '\'' +
                ", columnArticle='" + columnArticle + '\'' +
                ", columnProductCategory='" + columnProductCategory + '\'' +
                ", columnPrice='" + columnPrice + '\'' +
                ", columnOnStock='" + columnOnStock + '\'' +
                ", columnTnved='" + columnTnved + '\'' +
                ", columnBarcode='" + columnBarcode + '\'' +
                ", columnPriceOnStockOwn='" + columnPriceOnStockOwn + '\'' +
                ", columnOnStockOwn='" + columnOnStockOwn + '\'' +
                ", columnReservedOnStockOwn='" + columnReservedOnStockOwn + '\'' +
                ", columnFreeOnStock='" + columnFreeOnStock + '\'' +
                ", columnPriceForSiteOwn='" + columnPriceForSiteOwn + '\'' +
                ", columnProductName='" + columnProductName + '\'' +
                '}';
    }
}
