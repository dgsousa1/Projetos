package eapli.base.product.domain;

import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.utils.Unit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class ProductCodeTest {

    ProductCode instance;

    @Before
    public void before() {
        instance = new ProductCode("1200003", "320030000005");
    }

    /**
     * Test of GetManufacturingCode method, of class ProductCode.
     */
    @Test
    public void testGetManufacturingCode() {
        String exp = "1200003";
        assertEquals(exp,instance.getManufacturingCode());
    }

    /**
     * Test of GetManufacturingCode method, of class ProductCode.
     */
    @Test
    public void testGetCommercialCode() {
        String exp = "320030000005";
        assertEquals(exp,instance.getComercialCode());
    }

}