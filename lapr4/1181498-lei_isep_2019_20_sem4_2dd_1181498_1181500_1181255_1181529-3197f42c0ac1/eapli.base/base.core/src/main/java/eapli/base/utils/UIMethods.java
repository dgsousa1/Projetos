package eapli.base.utils;

import eapli.framework.util.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class with methods that might be useful for common UI activities
 */
public class UIMethods {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    /**
     * Method that asks the user a yes or no question via the console.
     * @param question String with the question (yes or no) to be asked to the user via Console
     * @return true if user chooses "Yes", and false if user chooses "No"
     */
    public static boolean askYN(String question){
        String valString;
        do {
            valString = Console.readLine(question+" Y/N");
        } while (!(valString.trim().equalsIgnoreCase("Y") || valString.trim().equalsIgnoreCase("N")));
        if (valString.equalsIgnoreCase("Y")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Asks the user for a date in String format, and then validates it.
     * @return a LocalDateTime object correspondent to the received String
     */
    public static LocalDateTime askForDate(){
        boolean loop = true;
        LocalDateTime providedDate = null;
        while (loop) {
            String date = Console.readLine("Date format: "+DATE_FORMAT);
            if (date.length() != 16) {
                System.out.println("Date provided is invalid");
                loop = true;
            } else {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
                    providedDate = LocalDateTime.parse(date, formatter);
                    loop = false;
                } catch (DateTimeParseException e) {
                    loop = true;
                    System.out.println("Date provided is invalid");
                }
            }
        }
        return providedDate;
    }




}
