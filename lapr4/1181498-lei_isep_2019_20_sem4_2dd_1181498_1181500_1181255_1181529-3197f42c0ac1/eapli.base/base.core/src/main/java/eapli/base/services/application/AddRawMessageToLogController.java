package eapli.base.services.application;


import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.domain.Machine;
import eapli.base.machine.repositories.MachineRepository;
import eapli.base.services.domain.RawMessage;
import eapli.base.services.repositories.RawMessageRepository;
import eapli.base.utils.RawMessageStructure;
import eapli.framework.validations.Preconditions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class AddRawMessageToLogController  {

    private final static String DIVIDER = ";";
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final RawMessageRepository messageRepository = PersistenceContext.repositories().rawMessage();
    private final MachineRepository machineRepository = PersistenceContext.repositories().machine();


    /**
     * Method that creates a raw message and adds it to the database
     * @param machineID id of the machine
     * @param message message that was imported
     * @return RawMessage object that was saved to the database
     */
    public RawMessage addRawMessageToLogs(Long machineID,  String message){
        RawMessage rawMessage = new RawMessage(machineID, message);
        return messageRepository.save(rawMessage);
    }

    /**
     * Method that validates a line of the file
     * @param line String that represents a line passed by parameter
     * @return returns the machine serial number, or -1 if error
     */
    public long validateRawMessage(String line){
        String[] values = line.split(DIVIDER);
        for(int i = 0 ; i<values.length ; i++){
            try {
                Preconditions.nonEmpty(values[i]);
            }catch(IllegalArgumentException e){
                return -1L;
            }
        }


        /*Check if message format exists */
        String format = RawMessageStructure.getMessageFormat(values[1]);
        if(format == null){
            return -1L;

        }

        /*Check for any missing parameters*/
        if(values.length != format.split(DIVIDER).length){
            return -1L;

        }


        /*Check if message exists*/
        Optional<RawMessage> rawMessage = findMessage(line);
        if(rawMessage.isPresent()){
            return -1L;
        }

        /*Check if machine exists in the system*/
        Optional<Machine> m = machineRepository.findByModelName(values[0]);
        if(!m.isPresent()){
            return -1L;

        }
        LocalDateTime date = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            date = LocalDateTime.parse(buildDateString(values[2]), formatter);
        } catch (DateTimeParseException e){
            return -1L;

        }
        if(date.isBefore(LocalDateTime.now())){
            return -1L;
        }
        return m.get().getSerialNumber();
    }

    /**
     * Method that checks if the message already exists in the system
     * @param name message
     * @return empty Optional if not found, or containing the Raw Message if found
     */
    private Optional<RawMessage> findMessage(String name){
        return this.messageRepository.findMessageInRepository(name);
    }

    /**
     * Method that parses the date in the parameter, to another format
     * @param date date in String
     * @return String with the formatted date
     */
    private String buildDateString(String date){
        String year = date.substring(0,4);
        String month = date.substring(4,6);
        String day = date.substring(6,8);
        String hour = date.substring(8,10);
        String minute = date.substring(10,12);
        String second = date.substring(12);


        return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;

    }





}
