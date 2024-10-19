package eapli.base.product.domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class ProductDescriptionTest {

    ProductDescription instance;

    @Before
    public void before() {
        instance = new ProductDescription("Blocos","Blocos Borracha");
    }

    /**
     * Test of GetBriefDescription method, of class ProductDescription.
     */
    @Test
    public void testGetBriefDescription() {
        instance = new ProductDescription("Blocos","Blocos Borracha");
        String exp = "Blocos";
        assertEquals(exp,instance.getBriefDescription());
    }



    /**
     * Test of GetCompleteDescription method, of class ProductDescription.
     */
    @Test
    public void testGetCompleteDescription() {
        instance = new ProductDescription("Blocos","Blocos Borracha");
        String exp = "Blocos Borracha";
        assertEquals(exp,instance.getCompleteDescription());
    }
}