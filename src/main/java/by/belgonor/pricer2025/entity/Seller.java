package by.belgonor.pricer2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "price_name", length = 100)
    private String priceName;

    @Size(max = 300)
    @Column(name = "path_to_price", length = 300)
    private String pathToPrice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "economic_rules", nullable = false)
    private RulesForBusiness economicRules;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "xls_price_rules", nullable = false)
    private RulesForXlsx xlsPriceRules;

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", priceName='" + priceName + '\'' +
                ", pathToPrice='" + pathToPrice + '\'' +
                ", economicRules=" + economicRules +
                ", xlsPriceRules=" + xlsPriceRules +
                '}';
    }
}