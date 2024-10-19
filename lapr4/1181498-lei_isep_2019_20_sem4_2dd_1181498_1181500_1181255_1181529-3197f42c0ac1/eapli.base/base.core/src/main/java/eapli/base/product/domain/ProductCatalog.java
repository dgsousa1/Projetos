package eapli.base.product.domain;

import eapli.framework.validations.Preconditions;

import java.util.List;

public class ProductCatalog {

    /**
     * List of all products.
     */
    private List<Product> products;

    /**
     *
     * @param products
     */
    public ProductCatalog(List<Product> products) {
        this.products = products;
    }

    /**
     * Returns the products.
     * @return products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Modifies the products.
     * @param products
     */
    public void setProducts(List<Product> products)
    {
        Preconditions.nonNull(products);
        this.products = products;
    }
}



