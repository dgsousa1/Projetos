package eapli.base.datamanagement.xml;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


/*source
https://stackoverflow.com/questions/4604497/xslt-processing-with-java
*/
public class XMLTransformation {

    public static void transformXML(String xslPath, String xmlPath, String outputPath) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(xslPath));
        Transformer transformer = null;
        transformer = factory.newTransformer(xslt);
        Source text = new StreamSource(new File(xmlPath));
        transformer.setOutputProperty(OutputKeys.VERSION,"4.0");
        transformer.transform(text, new StreamResult(new File(outputPath)));
    }


}
