package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductDescription implements ValueObject {

    @Column(length=30)
    private String briefDescription;

    @Column
    private String completeDescription;

    /**
     * Constructor responsible for getting the product description being used.
     * @param briefDescription
     * @param completeDescription
     */
    public ProductDescription(String briefDescription, String completeDescription) {
        this.briefDescription = briefDescription;
        this.completeDescription = completeDescription;
    }

    protected ProductDescription(){
        //for JPA
    }

    /**
     * Returns the brief description.
     * @return briefDescription
     */
    public String getBriefDescription() {
        return briefDescription;
    }

    /**
     * Returns the complete description.
     * @return completeDescription
     */
    public String getCompleteDescription() {
        return completeDescription;
    }

    @Override
    public String toString() {
        return " Brief Description: " + briefDescription +
                " Complete Description: " + completeDescription;
    }
}
