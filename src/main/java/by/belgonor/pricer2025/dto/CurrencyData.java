package by.belgonor.pricer2025.dto;

import java.math.BigDecimal;
//      ********************  ЭТО КУРСЫ ИЗ НБ   *******************
public class CurrencyData {
    private int code;
    private int quantity;
    private BigDecimal rate;

    public CurrencyData(int code, int quantity, BigDecimal rate) {
        this.code = code;
        this.quantity = quantity;
        this.rate = rate;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "CurrencyData{" +
                "code='" + code + '\'' +
                ", quantity=" + quantity +
                ", rate=" + rate +
                '}';
    }
}
