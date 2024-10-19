/*
package eapli.base.persistence.impl.jpa;

import eapli.base.productionorder.domain.Commission;
import eapli.base.productionorder.repositories.CommissionRepository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaCommissionRepository extends BasepaRepositoryBase<Commission,Long,Long>
    implements CommissionRepository {

    JpaCommissionRepository() {
        super("pkCommission");
    }

    @Override
    public Optional<Commission> findCommissionByCode(String code) {
        final TypedQuery<Commission> q = entityManager().createQuery(
                "SELECT c FROM Commission c WHERE c.commissionCode = :code",Commission.class
        );
        q.setParameter("code",code);

        try{
            Commission c = q.getSingleResult();
            return Optional.of(c);
        }catch(Exception e){
            return Optional.empty();
        }
    }

    @Override
    public List<Commission> findCommissionByProduct(String manufacturingCode) {
        final TypedQuery<Commission> q = entityManager().createQuery(
                "SELECT c FROM Commission c WHERE c.requestedProducts.product.codes.manufacturingCode = :code",Commission.class
        );
        q.setParameter("code",manufacturingCode);

        try{
            List<Commission> commissions = q.getResultList();
            return commissions;
        }catch(Exception e){
            return new ArrayList<Commission>();
        }
    }

}
*/