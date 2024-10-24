package by.belgonor.pricer2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "rules_for_business")
public class RulesForBusiness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_code", nullable = false)
    private CurrencyNb currencyCode;

    @NotNull
    @ColumnDefault("1.00")
    @Column(name = "currency_coefficient", nullable = false, precision = 5, scale = 2)
    private BigDecimal currencyCoefficient;

    @NotNull
    @ColumnDefault("1.00")
    @Column(name = "delivery_coefficient", nullable = false, precision = 4, scale = 2)
    private BigDecimal deliveryCoefficient;

    @NotNull
    @ColumnDefault("0.0000")
    @Column(name = "vat_percent_in_price", nullable = false, precision = 7, scale = 4)
    private BigDecimal vatPercentInPrice;

    @NotNull
    @ColumnDefault("1.00")
    @Column(name = "min_order_sum", nullable = false, precision = 7, scale = 2)
    private BigDecimal minOrderSum;

    @Override
    public String toString() {
        return "RulesForBusiness{" +
                "id=" + id +
                ", currencyCode=" + currencyCode +
                ", currencyCoefficient=" + currencyCoefficient +
                ", deliveryCoefficient=" + deliveryCoefficient +
                ", vatPercentInPrice=" + vatPercentInPrice +
                ", minOrderSum=" + minOrderSum +
                '}';
    }
}