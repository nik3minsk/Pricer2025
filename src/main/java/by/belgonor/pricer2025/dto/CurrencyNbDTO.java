package by.belgonor.pricer2025.dto;

import java.time.LocalDate;

public class CurrencyNbDTO {
    private Integer id;
    private LocalDate date;
    private Integer currencyCode;
    private Integer baseCurrencyCode;
    private String currencyName;

    public CurrencyNbDTO(LocalDate date, Integer currencyCode, Integer baseCurrencyCode, String currencyName) {
    }

    public CurrencyNbDTO() {
    }

    // Геттеры и сеттеры
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getBaseCurrencyCode() {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(Integer baseCurrencyCode) {
        this.baseCurrencyCode = baseCurrencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
