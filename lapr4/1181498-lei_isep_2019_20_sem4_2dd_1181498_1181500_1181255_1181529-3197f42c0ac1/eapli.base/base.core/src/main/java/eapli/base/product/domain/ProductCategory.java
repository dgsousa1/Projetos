package eapli.base.product.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class ProductCategory implements ValueObject {

    /**
     * Product's category.
     */
    private String productCategory;

    /**
     * Constructor responsible for getting the product category being used.
     * @param productCategory
     */
    public ProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    protected ProductCategory(){
        //for JPA
    }

    /**
     * Returns the product category.
     * @return productCategory
     */
    public String getProductCategory() {
        return productCategory;
    }

    /**
     * Modifies the product category.
     * @param productCategory
     */
    public void setProductCategory(String productCategory) {
        Preconditions.nonNull(productCategory);
        this.productCategory = productCategory;
    }

    @Override
    public String toString() {
        return productCategory;
    }
}
