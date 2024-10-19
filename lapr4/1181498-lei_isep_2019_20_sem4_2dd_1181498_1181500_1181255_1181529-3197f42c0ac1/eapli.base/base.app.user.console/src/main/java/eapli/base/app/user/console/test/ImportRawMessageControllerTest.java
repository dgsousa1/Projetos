package eapli.base.app.user.console.test;

import eapli.base.services.application.ImportRawMessageController;

import java.util.LinkedList;
import java.util.List;

public class ImportRawMessageControllerTest {

    public static void main(String[] args) {
        ImportRawMessageController controller = new ImportRawMessageController();
        List<String> filenames = new LinkedList<>();
        filenames.add("Mensagens.DD4");
        filenames.add("Mensagens.T3");
        controller.importRawMessageFromFile(filenames);







    }

}
