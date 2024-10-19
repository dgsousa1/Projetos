package eapli.base.product.domain;

import eapli.base.utils.MeasuredRawMaterial;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class BillOfMaterials implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkBillOfMaterial;

    @Version
    private Long version;

    @OneToOne(cascade=CascadeType.ALL)
    private Product finishedProduct;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MeasuredRawMaterial> listOfMaterials;

    private LocalDateTime issueDate;


    /**
     * Constructor responsible for getting the bill of materials code being used.
     * @param finishedProduct
     * @param listOfMaterials
     */
    public BillOfMaterials(Product finishedProduct, List<MeasuredRawMaterial> listOfMaterials) {
        this.finishedProduct = finishedProduct;
        this.listOfMaterials = listOfMaterials;
        this.issueDate = LocalDateTime.now();
    }

    public BillOfMaterials(Product finishedProduct, List<MeasuredRawMaterial> listOfMaterials,LocalDateTime issueDate) {
        this.finishedProduct = finishedProduct;
        this.listOfMaterials = listOfMaterials;
        this.issueDate=issueDate;
    }


    protected BillOfMaterials() {
        //for JPA
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return pkBillOfMaterial;
    }

    /**
     * Returns the finished product.
     * @return finishedProduct
     */
    public Product getFinishedProduct() {
        return finishedProduct;
    }

    /**
     * Returns the list of materials.
     * @return listOfMaterials
     */
    public List<MeasuredRawMaterial> getListOfMaterials() {
        return listOfMaterials;
    }

    /**
     * Returns the issue date.
     * @return issueDate
     */
    public LocalDateTime getIssueDate() {
        return issueDate;
    }
}
