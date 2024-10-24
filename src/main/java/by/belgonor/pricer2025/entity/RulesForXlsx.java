package by.belgonor.pricer2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "rules_for_xlsx")
public class RulesForXlsx {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "header_values", nullable = false)
    private XlsxHeaderValue headerValues;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "header_string_number", nullable = false)
    private Integer headerStringNumber;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "start_price_data_row_number", nullable = false)
    private Integer startPriceDataRowNumber;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "column_brand", nullable = false)
    private Integer columnBrand;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "column_article", nullable = false)
    private Integer columnArticle;

    @ColumnDefault("0")
    @Column(name = "column_product_category")
    private Integer columnProductCategory;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "column_price", nullable = false)
    private Integer columnPrice;

    @ColumnDefault("0")
    @Column(name = "column_on_stock")
    private Integer columnOnStock;

    @ColumnDefault("0")
    @Column(name = "column_tnved")
    private Integer columnTnved;

    @ColumnDefault("0")
    @Column(name = "column_barcode")
    private Integer columnBarcode;

    @ColumnDefault("0")
    @Column(name = "column_price_on_stock_own")
    private Integer columnPriceOnStockOwn;

    @ColumnDefault("0")
    @Column(name = "column_on_stock_own")
    private Integer columnOnStockOwn;

    @ColumnDefault("0")
    @Column(name = "column_reserved_on_stock_own")
    private Integer columnReservedOnStockOwn;

    @ColumnDefault("0")
    @Column(name = "column_free_on_stock")
    private Integer columnFreeOnStock;

    @ColumnDefault("0")
    @Column(name = "column_price_for_site_own")
    private Integer columnPriceForSiteOwn;

    @ColumnDefault("0")
    @Column(name = "column_product_name")
    private Integer columnProductName;

    @Override
    public String toString() {
        return "RulesForXlsx{" +
                "id=" + id +
                ", headerValues=" + headerValues +
                ", headerStringNumber=" + headerStringNumber +
                ", startPriceDataRowNumber=" + startPriceDataRowNumber +
                ", columnBrand=" + columnBrand +
                ", columnArticle=" + columnArticle +
                ", columnProductCategory=" + columnProductCategory +
                ", columnPrice=" + columnPrice +
                ", columnOnStock=" + columnOnStock +
                ", columnTnved=" + columnTnved +
                ", columnBarcode=" + columnBarcode +
                ", columnPriceOnStockOwn=" + columnPriceOnStockOwn +
                ", columnOnStockOwn=" + columnOnStockOwn +
                ", columnReservedOnStockOwn=" + columnReservedOnStockOwn +
                ", columnFreeOnStock=" + columnFreeOnStock +
                ", columnPriceForSiteOwn=" + columnPriceForSiteOwn +
                ", columnProductName=" + columnProductName +
                '}';
    }
}