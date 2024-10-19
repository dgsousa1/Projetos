package eapli.base.app.user.console.presentation.production;

import eapli.base.datamanagement.xml.XMLTransformation;
import eapli.base.datamanagement.xml.XMLTransformationController;
import eapli.base.utils.UIMethods;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.presentation.console.SelectWidget;
import eapli.framework.util.Console;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class XMLTransformationUI extends AbstractUI {
    @Override
    protected boolean doShow() {
        List<String> xmlFiles = new LinkedList<>();
        String chosenFile = null;
        final File folder = new File("xml");
        for (File file : folder.listFiles()) {
            String filename = file.getName();
            if(filename.contains(".xml")){
                xmlFiles.add(filename);
            }
        }

        final SelectWidget<String> selector = new SelectWidget<>("\n XML Files: ", xmlFiles);
        selector.show();
        chosenFile = selector.selectedElement();

        if(chosenFile==null){
            return true;
        }

        List<String> availableFiles = new LinkedList<>();
        String chosenTransformation = null;
        final File folderT = new File("xml/transformations");

        for (File file : folderT.listFiles()) {
            availableFiles.add(file.getName());
        }

        final SelectWidget<String> selectorT = new SelectWidget<>("\n XSL Files: ",availableFiles);
        selectorT.show();
        chosenTransformation = selectorT.selectedElement();

        if(chosenTransformation==null){
            return true;
        }

        String outputFile = "xml/transformations/outputs/OUTPUT"+chosenFile.split("\\.")[0];
        chosenTransformation="xml/transformations/"+chosenTransformation;
        chosenFile="xml/"+chosenFile;
        XMLTransformationController transformationController = new XMLTransformationController();
        if(transformationController.transformXML(chosenTransformation,chosenFile,outputFile)){
            System.out.println("Success");
            return true;
        } else{
            System.out.println("Failure");
            return false;
        }

    }

    @Override
    public String headline() {
        return "Transform XML";
    }
}
