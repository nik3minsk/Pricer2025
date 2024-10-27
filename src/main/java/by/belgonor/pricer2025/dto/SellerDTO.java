package by.belgonor.pricer2025.dto;

public class SellerDTO {
    private Integer id;
    private String priceName;
    private String pathToPrice;
    private Boolean isGeneralPrice;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getPathToPrice() {
        return pathToPrice;
    }

    public void setPathToPrice(String pathToPrice) {
        this.pathToPrice = pathToPrice;
    }

    public Boolean getIsGeneralPrice() {
        return isGeneralPrice;
    }

    public void setIsGeneralPrice(Boolean isGeneralPrice) {
        this.isGeneralPrice = isGeneralPrice;
    }
}
