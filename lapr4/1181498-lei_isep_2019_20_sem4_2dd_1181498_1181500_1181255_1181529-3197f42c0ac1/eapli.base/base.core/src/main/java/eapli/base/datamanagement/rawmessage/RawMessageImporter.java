package eapli.base.datamanagement.rawmessage;

import javafx.print.Printer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RawMessageImporter {


    /**
     * Imports a file, each line will be a String in a List
     * @param filename Filepath to be imported
     * @return List of Strings with the lines of the file
     * @throws IOException when no file
     */
    public List<String> importRawMessageFromFile(String filename) throws IOException {
        List<String> fileLines = new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader(filename));
        String line;

        while((line = bf.readLine())!= null) {
            fileLines.add(line);
        }

        return fileLines;

    }

    /**
     * Method to export the lines that were found with errors
     * @param filename file path of the file that will be exported
     * @param lines lines of the files that have erros
     * @return true after the method has finished
     * @throws IOException if no file
     */
    public boolean exportErrorRawMessages(String filename, List<String> lines) throws IOException {
       try ( BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
        writer.write("Raw Message Lines with errors");
        for (String l : lines) {
            writer.newLine();
            writer.write(l);
        }
       } catch (IOException e) {
           Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, e);
           throw new IOException("No file");
       }
        return true;

    }


}
