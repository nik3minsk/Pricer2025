//package by.belgonor.pricer2025.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import jakarta.validation.constraints.Size;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDate;
//
//@Getter
//@Setter
//@Entity
//@Table(name = "currency_nb")
//public class CurrencyNb {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Integer id;
//
//    @NotNull
//    @Column(name = "date", nullable = false)
//    private LocalDate date;
//
//    @NotNull
//    @Column(name = "currency_code", nullable = false)
//    private Integer currencyCode;
//
//    @NotNull
//    @Column(name = "base_currency_code", nullable = false)
//    private Integer baseCurrencyCode;
//
//    @Size(max = 100)
//    @Column(name = "currency_name", length = 100)
//    private String currencyName;
//
//    @Override
//    public String toString() {
//        return "CurrencyNb{" +
//                "id=" + id +
//                ", date=" + date +
//                ", currencyCode=" + currencyCode +
//                ", baseCurrencyCode=" + baseCurrencyCode +
//                ", currencyName='" + currencyName + '\'' +
//                '}';
//    }
//}