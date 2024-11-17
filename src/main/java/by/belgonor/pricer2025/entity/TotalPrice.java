package by.belgonor.pricer2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "total_price")
public class TotalPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_brand")
    private Brand idBrand;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_saler", nullable = false)
    private Seller idSaler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_code")
    private CurrencyNb currencyCode;

    @Column(name = "date")
    private LocalDate date;

    @Size(max = 200)
    @Column(name = "article", length = 200)
    private String article;

    @Size(max = 200)
    @Column(name = "product_category", length = 200)
    private String productCategory;

    @Column(name = "price", precision = 12, scale = 4)
    private BigDecimal price;

    @Size(max = 100)
    @Column(name = "on_stock", length = 100)
    private String onStock;

    @Size(max = 100)
    @Column(name = "tnved_code", length = 100)
    private String tnvedCode;

    @Size(max = 100)
    @Column(name = "barcode", length = 100)
    private String barcode;

    @ColumnDefault("0.0000")
    @Column(name = "price_on_stock_own", precision = 12, scale = 4)
    private BigDecimal priceOnStockOwn;

    @Column(name = "reservation_on_stock")
    private Double reservationOnStock;

    @Column(name = "free_on_stock")
    private Double freeOnStock;

    @Column(name = "price_for_sale_own", precision = 12, scale = 2)
    private BigDecimal priceForSaleOwn;

    @Size(max = 300)
    @Column(name = "product_name", length = 300)
    private String productName;

}