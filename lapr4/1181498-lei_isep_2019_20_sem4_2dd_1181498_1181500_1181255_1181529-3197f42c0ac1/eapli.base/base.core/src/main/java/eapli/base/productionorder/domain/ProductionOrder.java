package eapli.base.productionorder.domain;


import eapli.base.utils.MeasuredProduct;
import eapli.base.utils.MeasuredRawMaterial;
import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
public class ProductionOrder implements AggregateRoot<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pkProductionOrder;

    @Version
    private Long version;

    @Embedded
    private ProductionOrderCode productionOrderCode;
    @OneToMany
    private List<MeasuredRawMaterial> materialsConsumed;
    @Embedded
    private ProductionOrderSchedule productionOrderSchedule;
    @Embedded
    private ProductionOrderStatus productionOrderStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private MeasuredProduct productResult;

    private LocalDateTime issueDate;

    @ElementCollection
    private List<String> associatedCommissions;

    /**
     * Constructor responsible for getting the production order being used.
     * @param productionOrderCode
     * @param productionOrderSchedule
     * @param productResult
     * @param issueDate
     * @param associatedCommissions
     */
    public ProductionOrder(ProductionOrderCode productionOrderCode,
                           ProductionOrderSchedule productionOrderSchedule, MeasuredProduct productResult, LocalDateTime issueDate,
                            List<String> associatedCommissions) {
        this.productionOrderCode = productionOrderCode;
        this.productionOrderSchedule = productionOrderSchedule;
        this.productionOrderStatus = new ProductionOrderStatus(ProductionOrderStatusEnum.Pending);
        this.productResult = productResult;
        this.issueDate = issueDate;
        this.associatedCommissions=associatedCommissions;
    }

    protected ProductionOrder(){
        //for JPA
    }

    @Override
    public Long identity()   {
        return this.pkProductionOrder;
    }

    @Override
    public boolean sameAs(Object other)  {
        return DomainEntities.areEqual(this,other);
    }

    /**
     * Returns the production order code.
     * @return ordercode
     */
    public String getProductionOrderCode() {
        return productionOrderCode.getOrderCode();
    }

    /**
     * Returns the materials consumed.
     * @return materialsConsumed
     */
    public List<MeasuredRawMaterial> getMaterialsConsumed() {
        return materialsConsumed;
    }

    /**
     * Returns the production order schedule.
     * @return productionOrderSchedule
     */
    public ProductionOrderSchedule getProductionOrderSchedule() {
        return productionOrderSchedule;
    }

    /**
     * Returns the production order status.
     * @return productionOrderStatus
     */
    public ProductionOrderStatus getProductionOrderStatus() {
        return productionOrderStatus;
    }

    /**
     * Returns the product result.
     * @return productResult
     */
    public MeasuredProduct getProductResult() {
        return productResult;
    }

    /**
     * Returns the issued date.
     * @return issueDate
     */
    public LocalDateTime getIssueDate() { return issueDate; }

    /**
     * Returns the associated commissions.
     * @return associatedCommissions
     */
    public List<String> getAssociatedCommissions() { return associatedCommissions; }

    /**
     * Returns the production order status.
     * @return productionOrderStatus
     */
    public String getProductionOrderStatusString (){
        return this.productionOrderStatus.toString();
    }

    /**
     * Returns the expected start of execution.
     * @return expectedStartOfExecution
     */
    public LocalDateTime expectedStartOfExecution() {
      return this.productionOrderSchedule.getExpectedStartOfExecution();
    }


    /**
     * It gets the real start of execution.
     * @return realStartOfExecution or empty if there's nothing there.
     */
    public Optional<LocalDateTime> realStartOfExecution(){
        LocalDateTime realStartOfExecution = this.productionOrderSchedule.getRealStartOfExecution();
        try{
            Preconditions.noneNull(realStartOfExecution);
            return Optional.of(realStartOfExecution);
        }catch(IllegalArgumentException e){
            return  Optional.empty();
        }
    }

    /**
     * It gets the end of execution.
     * @return endOfExecution or empty if there's nothing there.
     */
    public Optional<LocalDateTime> endOfExecution(){
        LocalDateTime endExecution = this.productionOrderSchedule.getEndExecution();
        try{
            Preconditions.noneNull(endExecution);
            return Optional.of(endExecution);
        }catch(IllegalArgumentException e){
            return  Optional.empty();
        }
    }

    /**
     * It displays the gross execution time.
     * @return time or 0 if there's nothing to show.
     */
    public Long displayGrossExecutionTime(){
        Long time = this.productionOrderSchedule.getGrossExecutionTime();
        try{
            Preconditions.noneNull(time);
            return time;
        }catch(IllegalArgumentException e){
            return  0L;
        }

    }

    /**
     * It displays the effective execution time.
     * @return time or 0 if there's nothing to show.
     */
    public Long displayEffectiveExecutionTime(){
        Long time = this.productionOrderSchedule.getEffectiveExecutionTime();
        try{
            Preconditions.noneNull(time);
            return time;
        }catch(IllegalArgumentException e){
            return  0L;
        }

    }

    public void setMaterialsConsumed(List<MeasuredRawMaterial> materialsConsumed) {
        this.materialsConsumed = materialsConsumed;
    }

    @Override
    public String toString() {
        return "ProductionOrder{" +
                "pkProductionOrder=" + pkProductionOrder +
                ", version=" + version +
                ", productionOrderCode=" + productionOrderCode +
                ", materialsConsumed=" + materialsConsumed +
                ", productionOrderSchedule=" + productionOrderSchedule +
                ", productionOrderStatus=" + productionOrderStatus +
                ", productResult=" + productResult +
                ", issueDate=" + issueDate +
                ", associatedCommissions=" + associatedCommissions +
                '}';
    }
}
