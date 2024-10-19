package eapli.base.product.domain;

import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.utils.Unit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ProductCatalogTest {

    ProductCatalog instance;
    private List<Product> list = new ArrayList<>();

    @Before
    public void before() {
        Product p1 = new Product(new ProductCode("1111", "2222"),
                new ProductDescription("Product1", "First Product"),
                new ProductCategory("CAT1"),
                new RawMaterial("Raw Material Name",new Unit("UN")));

        Product p2 = new Product(new ProductCode ("2222", "3333"),
                new ProductDescription("Product2", "Second Product"),
                new ProductCategory("CAT2"),
                new RawMaterial("Raw Material Name",new Unit("UN")));
        list.add(p1);
        list.add(p2);

        instance = new ProductCatalog(list);
    }

    /**
     * Test of getProducts method, of class ProductCatalog.
     */
    @Test
   public void getProducts() {
        List<Product> test = instance.getProducts();
        assertEquals(test,list);
    }

    /**
     * Test of setProducts method, of class ProductCatalog.
     */
    @Test
    public void setProducts() {
        System.out.println("setProducts");
        List<Product> testList = new ArrayList<>();
        Product test1 = new Product(new ProductCode("4444", "5555"),
                new ProductDescription("Test1", "First Test"),
                new ProductCategory("CAT1"),
                new RawMaterial("Raw Material Name",new Unit("UN")));

        Product test2 = new Product(new ProductCode("5555", "6666"),
                new ProductDescription("Test2", "Second Test"),
                new ProductCategory("CAT2"),
                new RawMaterial("Raw Material Name",new Unit("UN")));
        testList.add(test1);
        testList.add(test2);

        instance.setProducts(testList);
        assertNotNull(instance.getProducts());
    }

    /**
     * Test of setProductsFail method, of class ProductCatalog.
     */
    @Test(expected = IllegalArgumentException.class)
   public void setProductsFail() {
        System.out.println("setProductsFail");
            instance.setProducts(null);
        }

}