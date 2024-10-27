package by.belgonor.pricer2025.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "sellers")
//@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
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

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_general_price", nullable = false)
    private Boolean isGeneralPrice = false;

}