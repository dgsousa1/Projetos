package eapli.base.material.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.validations.Preconditions;

import javax.persistence.Embeddable;

@Embeddable
public class TechnicalFile implements ValueObject {

    /**
     * Technical file name.
     */
    private String fileName;

    /**
     * Technical file path.
     */
    private String filePath;


    protected TechnicalFile(){
        //for JPA
    }

    /**
     * Constructor responsible for getting the technical file being used.
     * @param fileName
     * @param filePath
     */
    public TechnicalFile (String fileName, String filePath) {
        Preconditions.noneNull(fileName, filePath);

        this.fileName=fileName;
        this.filePath=filePath;
    }

    /**
     * Returns the filename.
     * @return fileName
     */
    public String getFilename() {
        return fileName;
    }

    /**
     * Modifies the filename.
     * @param fileName
     */
    public void setFilename(String fileName) {
        this.filePath = fileName;
    }

    /**
     * Returns the file path.
     * @return filePath
     */
    public String getFilepath() {
        return filePath;
    }

    /**
     * Modifies the file path.
     * @param filePath
     */
    public void setFilepath(String filePath) {
        this.filePath = filePath;
    }
}
