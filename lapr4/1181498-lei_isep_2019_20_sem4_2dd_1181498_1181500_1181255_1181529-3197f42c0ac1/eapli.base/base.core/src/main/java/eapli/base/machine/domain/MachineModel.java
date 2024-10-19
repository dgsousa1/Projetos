package eapli.base.machine.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class MachineModel implements ValueObject {

    /**
     * Machine model name
     */
    private String modelName;

    /**
     * Machine model description
     */
    private String description;

    /**
     * Machine model brand
     */
    private String brand;

    /**
     * Constructor responsible for getting the machine model being used.
     * @param modelName
     * @param description
     * @param brand
     */
    public MachineModel(String modelName, String description, String brand) {
        Preconditions.noneNull(modelName,description,brand);

        this.modelName = modelName;
        this.description = description;
        this.brand = brand;
    }

    protected MachineModel() {
        //for JPA
    }

    public String getModelName() {
        return modelName;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }
}