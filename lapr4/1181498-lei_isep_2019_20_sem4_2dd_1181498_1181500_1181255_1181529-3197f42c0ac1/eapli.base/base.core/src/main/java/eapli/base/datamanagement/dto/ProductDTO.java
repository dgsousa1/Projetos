package eapli.base.datamanagement.dto;

public class ProductDTO {

  private String manufacturingCode;
  private String commercialCode;
  private String briefDescription;
  private String completeDescription;
  private String productCategory;
  private String rawMaterialName;
  private String rawMaterialUnit;


    public ProductDTO(String manufacturingCode, String commercialCode, String briefDescription, String completeDescription, String productCategory, String rawMaterialName,String rawMaterialUnit) {
        this.manufacturingCode = manufacturingCode;
        this.commercialCode = commercialCode;
        this.briefDescription = briefDescription;
        this.completeDescription = completeDescription;
        this.productCategory = productCategory;
        this.rawMaterialName=rawMaterialName;
        this.rawMaterialUnit=rawMaterialUnit;
    }

    public String writeForUI(){
        return "Manufacturing Code: "+getManufacturingCode()+" Commercial Code: "+getCommercialCode()+" Brief Description: "+
                getBriefDescription()+" Complete Description: "+getCompleteDescription()+" Category: "+getProductCategory();
    }

    public String getManufacturingCode() {
        return manufacturingCode;
    }

    public void setManufacturingCode(String manufacturingCode) {
        this.manufacturingCode = manufacturingCode;
    }

    public String getCommercialCode() {
        return commercialCode;
    }

    public void setCommercialCode(String commercialCode) {
        this.commercialCode = commercialCode;
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

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }

    public String getRawMaterialUnit() {
        return rawMaterialUnit;
    }

    public void setRawMaterialUnit(String rawMaterialUnit) {
        this.rawMaterialUnit = rawMaterialUnit;
    }
}
