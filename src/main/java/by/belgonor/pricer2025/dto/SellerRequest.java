package by.belgonor.pricer2025.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class SellerRequest {
    private SellerDetails sellerDetails;
    private Rules_for_xlsx_columnsDTO rules_for_xlsx_columns;


    public SellerRequest() {
    }

    public SellerRequest(SellerDetails sellerDetails, Rules_for_xlsx_columnsDTO rules_for_xlsx_columns) {
        this.sellerDetails = sellerDetails;
        this.rules_for_xlsx_columns = rules_for_xlsx_columns;
    }

    @Override
    public String toString() {
        return "SellerRequest{" +
                "sellerDetails=" + sellerDetails +
                ", rules_for_xlsx_columns=" + rules_for_xlsx_columns +
                '}';
    }
}
