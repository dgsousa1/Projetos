package eapli.base.app.user.console.test;

import eapli.base.datamanagement.xml.XMLExportController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class XMLExportTest {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime providedDate = LocalDateTime.parse("2020-10-20 16:30", formatter);
        XMLExportController xmlExportController = new XMLExportController();

        xmlExportController.exportIntoXML("xml/aaaaaa.xml",null,providedDate,true,true,true,true,true,true,true,true);


    }
}
