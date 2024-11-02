package by.belgonor.pricer2025.entity;

import by.belgonor.pricer2025.dto.CurrencyNbDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.belgonor.pricer2025.service.CurrencyNbService;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.*;
import org.springframework.stereotype.Service;


public class AlfaBankCourses {
    private CurrencyNbService currencyNbService;

    String buyCode;
    String selllso;
    String buylso;
    String name;
    BigDecimal rate;
    String iso;
    Integer code;
    String date;

    Integer quantity;
    BigDecimal sellRate;
    BigDecimal buyRate;


    public String getBuyCode() {
        return buyCode;
    }

    public void setBuyCode(String buyCode) {
        this.buyCode = buyCode;
    }

    public String getSelllso() {
        return selllso;
    }

    public void setSelllso(String selllso) {
        this.selllso = selllso;
    }

    public String getBuylso() {
        return buylso;
    }

    public void setBuylso(String buylso) {
        this.buylso = buylso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSellRate() {
        return sellRate;
    }

    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    public BigDecimal getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(BigDecimal buyRate) {
        this.buyRate = buyRate;
    }

    public class Rates {
        //   private String name;
        private List<AlfaBankCourses> rates;

        @Override
        public String toString() {
            return "Rates{" +
                    "rates=" + rates +
                    '}';
        }

        public List<AlfaBankCourses> getRates() {
            return rates;
        }

        public void setRates(List<AlfaBankCourses> rates) {
            this.rates = rates;
        }
    }
}
