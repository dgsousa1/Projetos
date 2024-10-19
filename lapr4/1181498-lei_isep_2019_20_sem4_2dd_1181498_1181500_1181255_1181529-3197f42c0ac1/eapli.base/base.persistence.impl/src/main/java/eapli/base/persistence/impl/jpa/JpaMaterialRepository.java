package eapli.base.persistence.impl.jpa;

import eapli.base.material.domain.Material;
import eapli.base.material.domain.MaterialCategory;
import eapli.base.material.repositories.MaterialRepository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaMaterialRepository extends BasepaRepositoryBase<Material, Long, Long>
        implements MaterialRepository {

    JpaMaterialRepository() {
        super("pkMaterial");
    }

    @Override
    public List<Material> listAllMaterials(){
        final TypedQuery<Material> query = entityManager().createQuery(
                "SELECT m FROM Material m",Material.class
        );
        try{
            List<Material> m = query.getResultList();
            return m;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }


    @Override
    public List<MaterialCategory> findCategory(){
        final TypedQuery<MaterialCategory> query = entityManager().createQuery(
                "SELECT DISTINCT(mc) from MaterialCategory mc, Material m WHERE mc.pkMaterialCategory = m.pkMaterial",MaterialCategory.class
        );
        try{
            List<MaterialCategory> m = query.getResultList();
            return m;
        }catch(Exception e){
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Material> findByMaterialCode(String code) {
        final TypedQuery<Material> q = entityManager().createQuery(
                "SELECT m FROM Material m WHERE m.code.materialCode = :code",Material.class
        );
        q.setParameter("code",code);
        try{
            Material m = q.getSingleResult();
            return Optional.of(m);
        }catch(Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Material> findByMaterialName(String name) {
        final TypedQuery<Material> q = entityManager().createQuery(
                "SELECT m FROM Material m WHERE m.code.materialName = :name",Material.class
        );
        q.setParameter("name",name);
        try{
            Material m = q.getSingleResult();
            return Optional.of(m);
        }catch(Exception e){
            return Optional.empty();
        }
    }
}
