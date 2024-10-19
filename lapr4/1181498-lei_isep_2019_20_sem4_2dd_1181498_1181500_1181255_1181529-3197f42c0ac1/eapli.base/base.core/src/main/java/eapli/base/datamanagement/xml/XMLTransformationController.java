package eapli.base.datamanagement.xml;

import javax.xml.transform.TransformerException;

public class XMLTransformationController {

    public boolean transformXML(String xslPath, String xmlPath, String outputPath){
        try {
            XMLTransformation.transformXML(xslPath,xmlPath,outputPath);
        } catch (TransformerException e) {
            return false;
        }
        return true;
    }

}
