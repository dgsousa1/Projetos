package eapli.base.machine.domain;

import eapli.framework.domain.model.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ConfigurationFile implements ValueObject {
    /**
     * Configuration's file path.
     */
    private String filePath;
    /**
     * Configuration's file description
     */
    private String description;

    protected ConfigurationFile(){
        //for JPA
    }

    /**
     * Constructor responsible for getting the configuration file being used.
     * @param filePath
     * @param description
     */
    public ConfigurationFile(String filePath, String description) {
        this.filePath = filePath;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigurationFile that = (ConfigurationFile) o;
        return filePath.equals(that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePath, description);
    }

    public String configurationFilePath(){
        return filePath;
    }

    public String configurationFileDescription(){
        return description;
    }
}
