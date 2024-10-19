package eapli.base.persistence.impl.jpa;

import eapli.base.product.domain.Product;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.rawmaterial.repositories.RawMaterialRepository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaRawMaterialRepository extends BasepaRepositoryBase<RawMaterial,Long,Long>
        implements RawMaterialRepository {


    JpaRawMaterialRepository() {
        super("pkRawMaterial");
    }

    @Override
    public List<RawMaterial> listAllRawMaterials(){
        final TypedQuery<RawMaterial> query = entityManager().createQuery(
                "SELECT rm FROM RawMaterial rm",RawMaterial.class
        );
        try{
            List<RawMaterial> list = query.getResultList();
            return list;
        }catch(Exception e ){
            return new ArrayList<>();
        }
    }


    @Override
    public Optional<RawMaterial> findByRawMaterialName(String name) {
        final TypedQuery<RawMaterial> query = entityManager().createQuery(
                "SELECT rm FROM RawMaterial rm  WHERE rm.rawMaterialName= :name",RawMaterial.class
        );
        query.setParameter("name",name);
        try{
            RawMaterial rawMaterial = query.getSingleResult();
            return Optional.of(rawMaterial);
        }catch(Exception e){
            return Optional.empty();
        }
    }
}
