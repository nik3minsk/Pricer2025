package by.belgonor.pricer2025.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "xlsx_header_values")
public class XlsxHeaderValue {
    @Id
    @Column(name = "Column1", nullable = false)
    private Integer id;

    @Size(max = 200)
    @Column(name = "column_brand", length = 200)
    private String columnBrand;

    @Size(max = 200)
    @Column(name = "column_article", length = 200)
    private String columnArticle;

    @Size(max = 200)
    @Column(name = "column_product_category", length = 200)
    private String columnProductCategory;

    @Size(max = 200)
    @Column(name = "column_price", length = 200)
    private String columnPrice;

    @Size(max = 200)
    @Column(name = "column_on_stock", length = 200)
    private String columnOnStock;

    @Size(max = 200)
    @Column(name = "column_tnved", length = 200)
    private String columnTnved;

    @Size(max = 200)
    @Column(name = "column_barcode", length = 200)
    private String columnBarcode;

    @Size(max = 200)
    @Column(name = "column_price_on_stock_own", length = 200)
    private String columnPriceOnStockOwn;

    @Size(max = 200)
    @Column(name = "column_on_stock_own", length = 200)
    private String columnOnStockOwn;

    @Size(max = 200)
    @Column(name = "column_reserved_on_stock_own", length = 200)
    private String columnReservedOnStockOwn;

    @Size(max = 200)
    @Column(name = "column_free_on_stock", length = 200)
    private String columnFreeOnStock;

    @Size(max = 200)
    @Column(name = "column_price_for_site_own", length = 200)
    private String columnPriceForSiteOwn;

    @Size(max = 300)
    @Column(name = "column_product_name", length = 300)
    private String columnProductName;

}