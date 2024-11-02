package by.belgonor.pricer2025.converter;

import by.belgonor.pricer2025.dto.CurrencyNbDTO;
import by.belgonor.pricer2025.entity.CurrencyNb;

public class CurrencyNbConverter {
    public static CurrencyNbDTO toDTO(CurrencyNb currencyNb) {
        CurrencyNbDTO dto = new CurrencyNbDTO();
        dto.setId(currencyNb.getId());
        dto.setDate(currencyNb.getDate());
        dto.setCurrencyCode(currencyNb.getCurrencyCode());
        dto.setBaseCurrencyCode(currencyNb.getBaseCurrencyCode());
        dto.setCurrencyName(currencyNb.getCurrencyName());
        return dto;
    }

    public static CurrencyNb toEntity(CurrencyNbDTO dto) {
        CurrencyNb currencyNb = new CurrencyNb();
        currencyNb.setId(dto.getId());
        currencyNb.setDate(dto.getDate());
        currencyNb.setCurrencyCode(dto.getCurrencyCode());
        currencyNb.setBaseCurrencyCode(dto.getBaseCurrencyCode());
        currencyNb.setCurrencyName(dto.getCurrencyName());
        return currencyNb;
    }
}
