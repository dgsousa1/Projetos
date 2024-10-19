package eapli.base.persistence.impl.jpa;



import eapli.base.productionorder.domain.ProductionOrder;
import eapli.base.productionorder.domain.ProductionOrderStatusEnum;
import eapli.base.productionorder.repositories.ProductionOrderRepository;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaProductionOrderRepository extends BasepaRepositoryBase<ProductionOrder,Long, Long>
        implements ProductionOrderRepository {

    JpaProductionOrderRepository() {
        super("pkProductionOrder");
    }

    /**
     * Method to list all production orders in the database
     * @return a List of all production orders in the database, or an empty list if none were found
     */
    @Override
    public List<ProductionOrder> listAllProductionOrders() {
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p from ProductionOrder p",
                ProductionOrder.class
        );
        try{
            List<ProductionOrder> p = query.getResultList();
            return p;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    /**
     * Method to find a ProductionOrder in the database
     * @param code code of the ProductionOrder to be searched
     * @return Optional object containing a ProductionOrder, if not found in the database, it'll be empty
     */
    @Override
    public Optional<ProductionOrder> findProductionOrderByCode(String code) {
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p FROM ProductionOrder p  WHERE p.productionOrderCode.orderCode= :code",ProductionOrder.class
        );
        query.setParameter("code",code);

        try{
            ProductionOrder p = query.getSingleResult();
            return Optional.of(p);
        }catch(Exception e){
            return Optional.empty();
        }

    }

    @Override
    public Iterable<ProductionOrder> findProductionOrderByState(ProductionOrderStatusEnum status){
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT DISTINCT(po) FROM ProductionOrder po WHERE po.productionOrderStatus.productionOrderStatusEnum= :status",ProductionOrder.class
        );
        query.setParameter("status",status);
        try{
            List<ProductionOrder> po = query.getResultList();
            return po;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public List<ProductionOrder> findProductionOrderByCommission(String associatedCommission){
        final TypedQuery<ProductionOrder> query = entityManager().createQuery(
                "SELECT p FROM ProductionOrder p WHERE :associatedCommission MEMBER OF p.associatedCommissions",ProductionOrder.class
        );

        query.setParameter("associatedCommission",associatedCommission);

        try{
            List<ProductionOrder> p = query.getResultList();
            return p;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }
}