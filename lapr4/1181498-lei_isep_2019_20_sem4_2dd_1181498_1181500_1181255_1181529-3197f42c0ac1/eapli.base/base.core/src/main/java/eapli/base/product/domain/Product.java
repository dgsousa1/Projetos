package eapli.base.product.domain;

import eapli.base.datamanagement.dto.ProductDTO;
import eapli.base.rawmaterial.domain.RawMaterial;
import eapli.base.utils.Unit;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Product implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkProduct;

    @Version
    private Long version;

    @Embedded
    @Column(unique = true)
    private ProductCode codes;

    @Embedded
    private ProductDescription descriptions;

    @Embedded
    private ProductCategory productCategory;

    @OneToOne(cascade=CascadeType.ALL)
    private RawMaterial rawMaterial;

    /**
     * Constructor responsible for getting the product code being used.
     * @param codes
     * @param descriptions
     * @param productCategory
     * @param rawMaterial
     */
    public Product(ProductCode codes, ProductDescription descriptions,
                   ProductCategory productCategory, RawMaterial rawMaterial) {
        Preconditions.noneNull(codes, descriptions,  productCategory, rawMaterial);

        this.codes = codes;
        this.descriptions = descriptions;
        this.productCategory = productCategory;
        this.rawMaterial = rawMaterial;
    }

    public Product() {
        //for JPA
    }

    public ProductDTO toDTO(){
        return new ProductDTO(getProductManufacturingCode(),getProductCommercialCode(),getProductBriefDescription(),getProductCompleteDescription(),getProductCategory(),getRawMaterial().getRawMaterialName(),getRawMaterial().getProductUnit().toString());
    }

    /**
     * Returns the product manufacturing code.
     * @return manufacturing code
     */
    public String getProductManufacturingCode(){
        return this.codes.getManufacturingCode();
    }

    /**
     * Returns the product commercial code.
     * @return commercial code
     */
    public String getProductCommercialCode(){
        return this.codes.getComercialCode();
    }

    /**
     * Returns the product brief description.
     * @return brief description
     */
    public String getProductBriefDescription(){
        return this.descriptions.getBriefDescription();
    }

    /**
     * Returns the product complete description.
     * @return complete description.
     */
    public String getProductCompleteDescription(){
        return this.descriptions.getCompleteDescription();
    }


    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return pkProduct;
    }


    /**
     * Returns the descriptions.
     * @return descriptions
     */
    public ProductDescription getDescriptions() {
        return descriptions;
    }

    /**
     * Returns the product category.
     * @return productCategory
     */
    public String getProductCategory() {
        return productCategory.toString();
    }

    /**
     * Returns the raw material.
     * @return rawMaterial
     */
    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    @Override
    public int compareTo(Long other) {
        return pkProduct.compareTo(other);
    }


}
