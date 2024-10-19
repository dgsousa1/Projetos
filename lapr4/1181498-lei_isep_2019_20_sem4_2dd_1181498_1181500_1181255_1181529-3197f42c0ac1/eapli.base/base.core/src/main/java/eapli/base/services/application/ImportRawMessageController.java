package eapli.base.services.application;


import java.util.List;

public class ImportRawMessageController {


    /**
     * Method that will create a thread for each file in the parameter
     * @param filenames list of filenames that are to be imported
     * @return boolean after the method has finished
     */
    public boolean importRawMessageFromFile(List<String> filenames){
        for(String filename : filenames){
            new Thread(new FileThread(filename)).start();
        }

        return true;
    }



}
