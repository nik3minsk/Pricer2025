// src/main/java/by/belgonor/pricer2025/dto/RulesForBusinessDTO.java
package by.belgonor.pricer2025.dto;

import by.belgonor.pricer2025.entity.CurrencyNb;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Data
public class RulesForBusinessDTO {
    private Integer id;
    private CurrencyNb currencyCode;
    private BigDecimal currencyCoefficient;
    private BigDecimal deliveryCoefficient;
    private BigDecimal vatPercentInPrice;
    private BigDecimal minOrderSum;


}
