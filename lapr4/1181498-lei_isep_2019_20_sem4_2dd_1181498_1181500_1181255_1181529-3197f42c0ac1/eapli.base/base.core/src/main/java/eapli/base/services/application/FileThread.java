package eapli.base.services.application;

import eapli.base.datamanagement.rawmessage.RawMessageImporter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileThread implements Runnable {

    private String filename;
    private String errorFilename;
    private final AddRawMessageToLogController addRawMessageToLogController = new AddRawMessageToLogController();
    private final RawMessageImporter rawMessageImporter = new RawMessageImporter();

    public FileThread(String filename) {
        this.filename="entrydirectory/"+filename;
        this.errorFilename = "processedmsgs/ERROR"+filename;
    }

    /**
     *  Method runs when this thread is created
     */
    @Override
    public void run() {
        List<String> errorLines = new ArrayList<>();
        List<String> lines = null;
        try {
            /* gets the lines from the file that was provided via parameter*/
            lines = rawMessageImporter.importRawMessageFromFile(this.filename);
        } catch (IOException e) {
            return;
        }

        for(String l : lines){
            /* validates each line that came from the file*/
            Long id = addRawMessageToLogController.validateRawMessage(l);
            if(id != -1){
                /* adds the raw message to the database*/
                addRawMessageToLogController.addRawMessageToLogs(id,l);
            }else{
                errorLines.add(l);
            }

        }

        try {
            /*exports any invalid lines to a file*/
            rawMessageImporter.exportErrorRawMessages(errorFilename,errorLines);
        } catch (IOException e) {
            return;
        }
    }
}
