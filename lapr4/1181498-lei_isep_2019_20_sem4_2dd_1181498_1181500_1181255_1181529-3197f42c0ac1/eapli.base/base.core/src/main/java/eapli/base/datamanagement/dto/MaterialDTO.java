package eapli.base.datamanagement.dto;

public class MaterialDTO {
    String materialCode;
    String materialName;
    String materialCategoryCode;
    String materialCategoryDescription;
    String materialFileName;
    String materialFilePath;
    String rawMaterialUnit;
    String rawMaterialName;

    public MaterialDTO(String materialCode, String materialName, String materialCategoryCode, String materialCategoryDescription, String materialFileName, String materialFilePath, String rawMaterialUnit, String rawMaterialName) {
        this.materialCode = materialCode;
        this.materialName = materialName;
        this.materialCategoryCode = materialCategoryCode;
        this.materialCategoryDescription = materialCategoryDescription;
        this.materialFileName = materialFileName;
        this.materialFilePath = materialFilePath;
        this.rawMaterialUnit = rawMaterialUnit;
        this.rawMaterialName = rawMaterialName;
    }


    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCategoryCode() {
        return materialCategoryCode;
    }

    public void setMaterialCategoryCode(String materialCategoryCode) {
        this.materialCategoryCode = materialCategoryCode;
    }

    public String getMaterialCategoryDescription() {
        return materialCategoryDescription;
    }

    public void setMaterialCategoryDescription(String materialCategoryDescription) {
        this.materialCategoryDescription = materialCategoryDescription;
    }

    public String getMaterialFileName() {
        return materialFileName;
    }

    public void setMaterialFileName(String materialFileName) {
        this.materialFileName = materialFileName;
    }

    public String getMaterialFilePath() {
        return materialFilePath;
    }

    public void setMaterialFilePath(String materialFilePath) {
        this.materialFilePath = materialFilePath;
    }

    public String getRawMaterialUnit() {
        return rawMaterialUnit;
    }

    public void setRawMaterialUnit(String rawMaterialUnit) {
        this.rawMaterialUnit = rawMaterialUnit;
    }

    public String getRawMaterialName() {
        return rawMaterialName;
    }

    public void setRawMaterialName(String rawMaterialName) {
        this.rawMaterialName = rawMaterialName;
    }
}
