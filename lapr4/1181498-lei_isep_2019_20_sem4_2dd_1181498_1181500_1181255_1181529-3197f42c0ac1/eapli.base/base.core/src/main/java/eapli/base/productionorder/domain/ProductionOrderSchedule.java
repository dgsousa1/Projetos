package eapli.base.productionorder.domain;

import eapli.framework.domain.model.ValueObject;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class ProductionOrderSchedule implements ValueObject {

    private LocalDateTime expectedStartOfExecution;
    private LocalDateTime realStartOfExecution;
    private LocalDateTime endExecution;
    //tempo bruto de execução (i.e. todo o tempo decorrido)
    private Long grossExecutionTime;
    //tempo efetivo de execução (i.e. não considera paragens devido a falhas ocorridas)
    private Long effectiveExecutionTime;

    protected ProductionOrderSchedule(){
        //for JPA
    }

    /**
     * Constructor responsible for getting the production order schedule being used.
     * @param expectedStartOfExecution
     */
    public ProductionOrderSchedule(LocalDateTime expectedStartOfExecution) {
        this.expectedStartOfExecution = expectedStartOfExecution;
    }

    /**
     * Modifies the expected start of execution.
     * @param expectedStartOfExecution
     */
    public void setExpectedStartOfExecution(LocalDateTime expectedStartOfExecution) {
        this.expectedStartOfExecution = expectedStartOfExecution;
    }

    /**
     * Modifies the real start of execution.
     * @param realStartOfExecution
     */
    public void setRealStartOfExecution(LocalDateTime realStartOfExecution) {
        this.realStartOfExecution = realStartOfExecution;
    }

    /**
     * Modifies the end of execution.
     * @param endExecution
     */
    public void setEndExecution(LocalDateTime endExecution) {
        this.endExecution = endExecution;
    }

    /**
     * Modifies the gross execution time.
     * @param grossExecutionTime
     */
    public void setGrossExecutionTime(Long grossExecutionTime) {
        this.grossExecutionTime = grossExecutionTime;
    }

    /**
     * Modifies the effective execution time.
     * @param effectiveExecutionTime
     */
    public void setEffectiveExecutionTime(Long effectiveExecutionTime) {
        this.effectiveExecutionTime = effectiveExecutionTime;
    }

    /**
     * Returns the expected start of execution.
     * @return expectedStartOfExecution
     */
    public LocalDateTime getExpectedStartOfExecution() {
        return expectedStartOfExecution;
    }

    /**
     * Returns the real start of execution.
     * @return realStartOfExecution
     */
    public LocalDateTime getRealStartOfExecution() {
        return realStartOfExecution;
    }

    /**
     * Returns the end of execution.
     * @return endExecution
     */
    public LocalDateTime getEndExecution() {
        return endExecution;
    }

    /**
     * Returns the gross execution time.
     * @return grossExecutionTime
     */
    public Long getGrossExecutionTime() {
        return grossExecutionTime;
    }

    /**
     * Returns the effective execution time.
     * @return effectiveExecutionTime
     */
    public Long getEffectiveExecutionTime() {
        return effectiveExecutionTime;
    }
}
