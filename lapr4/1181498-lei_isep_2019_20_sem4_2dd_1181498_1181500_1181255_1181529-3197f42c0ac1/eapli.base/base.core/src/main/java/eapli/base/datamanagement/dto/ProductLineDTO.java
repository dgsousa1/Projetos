package eapli.base.datamanagement.dto;

import eapli.framework.representations.dto.DTO;

import java.lang.annotation.Annotation;

public class ProductLineDTO {

    String manufacturingCode;
    String comercialCode;
    String briefDescription;
    String completeDescription;
    String unit;
    String productCategory;
    String errorMessage;

    public ProductLineDTO(String manufacturingCode, String comercialCode, String briefDescription, String completeDescription, String unit, String productCategory, String errorMessage) {
        this.manufacturingCode = manufacturingCode;
        this.comercialCode = comercialCode;
        this.briefDescription = briefDescription;
        this.completeDescription = completeDescription;
        this.unit = unit;
        this.productCategory = productCategory;
        this.errorMessage = errorMessage;
    }

    public String getManufacturingCode() {
        return manufacturingCode;
    }

    public void setManufacturingCode(String manufacturingCode) {
        this.manufacturingCode = manufacturingCode;
    }

    public String getComercialCode() {
        return comercialCode;
    }

    public void setComercialCode(String comercialCode) {
        this.comercialCode = comercialCode;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getCompleteDescription() {
        return completeDescription;
    }

    public void setCompleteDescription(String completeDescription) {
        this.completeDescription = completeDescription;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
