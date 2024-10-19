package eapli.base.machine.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.validations.Preconditions;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Machine implements AggregateRoot<Long> {

    @Id
    private Long serialNumber;

    @Version
    private Long version;

    @Embedded
    private MachineModel machineModel;

    @ElementCollection
    private List<ConfigurationFile> configurationFileList;

    /**
     * Constructor responsible for getting the machine being used.
     * @param serialNumber
     * @param machineModel
     */
    public Machine(Long serialNumber, MachineModel machineModel) {
        Preconditions.noneNull(serialNumber, machineModel);

        this.serialNumber = serialNumber;
        this.machineModel = machineModel;
        this.configurationFileList = new ArrayList<>();
    }

    protected Machine(){
        //for JPA
    }

    /**
     * Returns the configuration file list
     * @return configurationFileList
     */
    public List<ConfigurationFile> getConfigurationFileList() {
        return configurationFileList;
    }

    /**
     * Add a configuration file to the system.
     * @param configurationFile
     * @return true if the configuration file was introduced in the system and false
     * if the opposite occurs.
     */
    public boolean addConfigurationFile(ConfigurationFile configurationFile){
        if(configurationFileList.contains(configurationFile)){
            return false;
        }
        return configurationFileList.add(configurationFile);
    }

    @Override
    public boolean sameAs(Object other) {
        return DomainEntities.areEqual(this,other);
    }

    @Override
    public Long identity() {
        return serialNumber;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public MachineModel getMachineModel() {
        return machineModel;
    }
}
