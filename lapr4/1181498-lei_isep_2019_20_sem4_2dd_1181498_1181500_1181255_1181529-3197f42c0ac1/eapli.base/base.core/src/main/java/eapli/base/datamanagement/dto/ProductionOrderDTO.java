package eapli.base.datamanagement.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProductionOrderDTO {

    String orderCode;
    String issueDate;
    String expectedStartOfExecution;
    String manufacturingCode;
    String quantity;
    String unit;
    String associatedCommissions;
    String errorType;

    public ProductionOrderDTO(String orderCode, String issueDate, String expectedStartOfExecution, String manufacturingCode, String quantity, String unit, String associatedCommissions, String errorType) {
        this.orderCode = orderCode;
        this.issueDate = issueDate;
        this.expectedStartOfExecution = expectedStartOfExecution;
        this.manufacturingCode = manufacturingCode;
        this.quantity = quantity;
        this.unit = unit;
        this.associatedCommissions = associatedCommissions;
        this.errorType = errorType;
    }


    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpectedStartOfExecution() {
        return expectedStartOfExecution;
    }

    public void setExpectedStartOfExecution(String expectedStartOfExecution) {
        this.expectedStartOfExecution = expectedStartOfExecution;
    }

    public String getManufacturingCode() {
        return manufacturingCode;
    }

    public void setManufacturingCode(String manufacturingCode) {
        this.manufacturingCode = manufacturingCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAssociatedCommissions() {
        return associatedCommissions;
    }

    public void setAssociatedCommissions(String associatedCommissions) {
        this.associatedCommissions = associatedCommissions;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
}
