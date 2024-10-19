package eapli.base.app.user.console.presentation.production;

import eapli.base.services.application.ImportRawMessageController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ImportMessageFileUI extends AbstractUI {

    ImportRawMessageController importRawMessageController = new ImportRawMessageController();

    /**
     * method that displays a menu that allows the user to import raw messages from files
     * @return true if the operation ran successfully and false the opposite occurs.
     */
    @Override
    protected boolean doShow() {
        List<String> availableFiles = new LinkedList<>();
        List<String> chosenFiles = new LinkedList<>();
        System.out.println("Listing all Files in Directory");
        System.out.println("0.Exit");
        /* Displays all the files on the entrydirectory to be imported*/
        final File folder = new File("entrydirectory");
        boolean loop = true;
        while(loop) {
            int index = 1;
            for (final File fileEntry : folder.listFiles()) {
                System.out.println(index + "." + " " + fileEntry.getName());
                availableFiles.add(fileEntry.getName());
                index++;
            }

            /* User chooses one or more files*/
            int choice = 0;
            boolean choiceLoop = true;
            while(choiceLoop){
                choice = Console.readInteger("Choose a file: ");
                if(choice < 0 || choice > index){
                    System.out.println("Invalid choice");
                    choiceLoop = true;
                }else{
                    choiceLoop= false;
                }
            }

            if (choice == 0) {
                System.out.println("Exiting");
                return true;
            }

            try {
                chosenFiles.add(availableFiles.remove(choice - 1));
            } catch (Exception e) {
                System.out.println("File is not available");
            }
            String valString;
            do {
                valString = Console.readLine("Do you want to add more files?");
            } while (!(valString.trim().equals("Y") || valString.trim().equals("N")));
            if (valString.equals("Y")) {
                loop = true;
            } else {
                loop = false;
            }
        }
        System.out.println("Files to be imported:");
        System.out.println("======================");
        for(String s : chosenFiles){
            System.out.println(s+" Errors will be exported to: processedmsgs/ERROR"+s);
        }
        System.out.println("======================");


        importRawMessageController.importRawMessageFromFile(chosenFiles);

        return true;
    }

    @Override
    public String headline() {
        return "Import Messages From File";
    }
}
