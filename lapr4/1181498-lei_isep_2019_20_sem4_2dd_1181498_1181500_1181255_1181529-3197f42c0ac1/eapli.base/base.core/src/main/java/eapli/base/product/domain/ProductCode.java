package eapli.base.product.domain;


import eapli.framework.domain.model.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductCode implements ValueObject {

    @Column(length=15,unique=true)
    private String manufacturingCode;

    @Column(length=15,unique=true)
    private String comercialCode;

    /**
     * Constructor responsible for getting the product code being used.
     * @param manufacturingCode
     * @param commercialCode
     */
    public ProductCode(String manufacturingCode, String commercialCode) {
        this.manufacturingCode = manufacturingCode;
        this.comercialCode = commercialCode;
    }

    protected ProductCode(){
        //for JPA
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCode that = (ProductCode) o;
        return Objects.equals(getManufacturingCode(), that.getManufacturingCode()) &&
                Objects.equals(getComercialCode(), that.getComercialCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getManufacturingCode(), getComercialCode());
    }

    /**
     * Returns the manufacturing code.
     * @return manufacturingCode
     */
    public String getManufacturingCode() {
        return manufacturingCode;
    }

    /**
     * Returns the commercial code.
     * @return comercialCode
     */
    public String getComercialCode() {
        return comercialCode;
    }

    @Override
    public String toString() {
        return " Manufacturing Code: " + manufacturingCode +
                " Commercial Code: " + comercialCode +
                ' ';
    }
}
