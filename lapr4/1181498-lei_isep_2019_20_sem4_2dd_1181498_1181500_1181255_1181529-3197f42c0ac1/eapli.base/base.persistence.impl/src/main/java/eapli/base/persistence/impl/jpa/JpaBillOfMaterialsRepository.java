package eapli.base.persistence.impl.jpa;

import eapli.base.product.domain.BillOfMaterials;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.BillOfMaterialsRepository;
import eapli.base.utils.MeasuredRawMaterial;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaBillOfMaterialsRepository extends BasepaRepositoryBase<BillOfMaterials,Long,Long>
    implements BillOfMaterialsRepository {

    JpaBillOfMaterialsRepository() { super("pkBillOfMaterial"); }

    /**
     * Find a Bill of Materials by the Product's manufacturing code
     * @param manufacturingCode Manufacturing Code of a Product
     * @return a Optional object containing a Bill Of Materials, or empty if not found.
     */
    @Override
    public Optional<BillOfMaterials> findProductBillOfMaterials(String manufacturingCode) {
        final TypedQuery<BillOfMaterials> q = entityManager().createQuery(
                "SELECT b FROM BillOfMaterials b WHERE b.finishedProduct.codes.manufacturingCode =:code",BillOfMaterials.class
        );
        q.setParameter("code",manufacturingCode);

        try{
            BillOfMaterials c = q.getSingleResult();
            return Optional.of(c);
        }catch(Exception e){
            return Optional.empty();
        }
    }

    /**
     * Method that returns all the Products that aren't associated to a Bill Of Materials
     * @return List with all products not associated, or an empty list if none were found.
     */
    @Override
    public List<Product> productsWithNoBillOfMaterials() {
        final TypedQuery<Product> query = entityManager().createQuery(
                "SELECT p from Product p WHERE p.codes.manufacturingCode NOT IN (SELECT DISTINCT(product.codes.manufacturingCode) FROM BillOfMaterials z JOIN z.finishedProduct product )",
                Product.class
        );
        try{
            List<Product> p = query.getResultList();
            return p;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }



}
