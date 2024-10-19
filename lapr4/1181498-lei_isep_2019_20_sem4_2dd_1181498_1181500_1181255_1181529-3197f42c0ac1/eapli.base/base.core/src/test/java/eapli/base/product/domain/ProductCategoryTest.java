package eapli.base.product.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ProductCategoryTest {

    ProductCategory instance;

    @Before
    public void before() {
        instance = new ProductCategory("ME-BR");;
    }

    /**
     * Test of GetProductCategory method, of class ProductCategory.
     */
    @Test
    public void testGetProductCategory() {
        String exp = "ME-BR";
        assertEquals(exp,instance.getProductCategory());
    }


    /**
     * Test of setProductCategory method, of class ProductCategory.
     */
    @Test
    public void testSetProductCategory() {
        System.out.println("SetProductCategory");
        instance.setProductCategory("ME-BR");
        assertNotNull(instance.getProductCategory());
    }

    /**
     * Test of setProductCategoryFail method, of class ProductCategory.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetProductCategoryFail() {
        System.out.println("SetProductCategory");
            instance.setProductCategory(null);

    }

}
