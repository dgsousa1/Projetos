package eapli.base.material.domain;

import eapli.base.datamanagement.dto.MaterialDTO;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.List;

@Entity
public class Material implements AggregateRoot<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkMaterial;

    @Version
    private Long version;

    @Column(unique = true)
    private MaterialCode code;

    @ManyToOne(cascade=CascadeType.ALL)
    private MaterialCategory materialCategory;

    private TechnicalFile technicalFile;

    @OneToOne(cascade=CascadeType.ALL)
    private RawMaterial rawMaterial;

    protected Material(){
        //for JPA
    }

    /**
     * Constructor responsible for getting the material being used.
     * @param materialCode
     * @param materialCategory
     * @param technicalFile
     * @param rawMaterial
     */
    public Material(MaterialCode materialCode, MaterialCategory materialCategory, TechnicalFile technicalFile,
                    RawMaterial rawMaterial) {
        Preconditions.noneNull(materialCode, materialCategory, technicalFile, rawMaterial);

        this.code = materialCode;
        this.materialCategory = materialCategory;
        this.technicalFile = technicalFile;
        this.rawMaterial = rawMaterial;
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof Material)) {
            return false;
        }

        final Material that = (Material) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }

    /**
     * Returns the material code.
     * @return code
     */
    public MaterialCode getCode() {
        return code;
    }

   public MaterialDTO toDTO(){
        return  new MaterialDTO(this.code.getMaterialCode(),this.code.getMaterialName(),this.materialCategory.getCategoryCode(),
       this.materialCategory.getCategoryDescription(),this.technicalFile.getFilename(),this.technicalFile.getFilepath(),this.rawMaterial.getProductUnit().toString(),
       this.rawMaterial.getRawMaterialName());
   }

    @Override
    public Long identity() {
        return pkMaterial;
    }
}
