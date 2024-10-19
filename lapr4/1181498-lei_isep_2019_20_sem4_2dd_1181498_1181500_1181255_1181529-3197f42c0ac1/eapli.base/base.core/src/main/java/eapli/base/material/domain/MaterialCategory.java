package eapli.base.material.domain;

import eapli.framework.domain.model.DomainEntity;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class MaterialCategory implements DomainEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkMaterialCategory;

    @Version
    private Long version;

    @Column(length = 10)
    private String categoryCode;

    @Column(length = 50, nullable = false)
    private String categoryDescription;

    protected MaterialCategory() {
        //for JPA
    }

    /**
     * Constructor responsible for getting the material category being used.
     * @param categoryCode
     * @param categoryDescription
     */
    public MaterialCategory(String categoryCode, String categoryDescription) {
        Preconditions.noneNull(categoryCode, categoryDescription);

        this.categoryCode = categoryCode;
        this.categoryDescription = categoryDescription;
    }

    /**
     * Returns the category code
     * @return categoryCode
     */
    public String getCategoryCode() {
        return categoryCode;
    }

    /**
     * Modifies the category code.
     * @param categoryCode
     */
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    /**
     * Returns the category description.
     * @return categoryDescription
     */
    public String getCategoryDescription() {
        return categoryDescription;
    }

    /**
     * Modifies the category code.
     * @param categoryDescription
     */
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        eapli.base.material.domain.MaterialCategory that = (eapli.base.material.domain.MaterialCategory) o;
        return Objects.equals(getCategoryCode(), that.getCategoryCode()) &&
                Objects.equals(getCategoryDescription(), that.getCategoryDescription());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getCategoryCode(), getCategoryDescription());
    }

    @Override
    public boolean sameAs(Object other) {
        if (!(other instanceof MaterialCategory)) {
            return false;
        }

        final MaterialCategory that = (MaterialCategory) other;
        if (this == that) {
            return true;
        }

        return identity().equals(that.identity());
    }


    @Override
    public String toString() {
        return "MaterialCategory{" + "categoryCode='" + categoryCode + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                '}';
    }

    @Override
    public Long identity() {
        return pkMaterialCategory;
    }
}
