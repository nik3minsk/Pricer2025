package by.belgonor.pricer2025.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "currency_rate")
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private CurrencyNb currency;

    @NotNull
    @Column(name = "currency_rate", nullable = false, precision = 10, scale = 6)
    private BigDecimal currencyRate;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "id=" + id +
                ", currency=" + currency +
                ", currencyRate=" + currencyRate +
                ", date=" + date +
                '}';
    }
}