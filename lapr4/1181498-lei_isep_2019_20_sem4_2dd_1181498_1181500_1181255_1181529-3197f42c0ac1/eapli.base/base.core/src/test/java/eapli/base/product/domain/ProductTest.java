package eapli.base.product.domain;

import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.utils.Unit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductTest {

    Product product;
    ProductCode productCode;
    ProductDescription productDescription;
    ProductCategory productCategory;
    Unit unit;
    RawMaterial rawMaterial;

    @Before
    public void before(){
        productCode = new ProductCode("1200003","320030000005");
        productDescription = new ProductDescription("Blocos","Blocos Borracha");
        productCategory = new ProductCategory("ME-BR");
        unit = new Unit("KG");
        rawMaterial = new RawMaterial("Borracha", unit);

        product = new Product(productCode,productDescription,productCategory,rawMaterial);
    }

    /**
     * Test of GetCodes method, of class Product.
     */
    @Test
    public void testGetCodes() {
        assertNotNull(product.getProductManufacturingCode());
        assertNotNull(product.getProductBriefDescription());

    }

    /**
     * Test of GetDescriptions method, of class Product.
     */
    @Test
    public void getDescriptions() { assertNotNull(product.getDescriptions()); }

    /**
     * Test of GetProductCategory method, of class Product
     */
    @Test
    public void getProductCategory() { assertNotNull(product.getProductCategory()); }

    /**
     * Test of GetRawMaterial method, of class RawMaterial
     */
    @Test
    public void getRawMaterial() { assertNotNull(product.getRawMaterial()); }
}