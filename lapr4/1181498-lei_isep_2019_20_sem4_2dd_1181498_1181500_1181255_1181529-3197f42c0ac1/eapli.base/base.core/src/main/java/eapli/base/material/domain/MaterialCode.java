package eapli.base.material.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Preconditions;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MaterialCode implements ValueObject {
    @Column(length=50,unique=true)
    private String materialCode;

    private String materialName;

    /**
     * Constructor responsible for getting the material code being used.
     * @param materialCode
     * @param materialName
     */
    public MaterialCode(String materialCode, String materialName) {
        Preconditions.noneNull(materialCode, materialName);

        this.materialCode = materialCode;
        this.materialName = materialName;
    }

    protected MaterialCode() {
        //for JPA
    }

    /**
     * Returns the material code.
     * @return materialCode
     */
    public String getMaterialCode() {
        return materialCode;
    }

    /**
     * Returns the material name.
     * @return materialName
     */
    public String getMaterialName() {
        return materialName;
    }
}
