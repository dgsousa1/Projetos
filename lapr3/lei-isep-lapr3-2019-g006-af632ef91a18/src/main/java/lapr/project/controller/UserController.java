package lapr.project.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.UserRegistration;
import lapr.project.model.Client;
import lapr.project.utils.SendMail;

/**
 *
 *
 */
public class UserController {

    /**
     * User manager.
     */
    UserRegistration registration;
    
    /**
     *  A service that provides sending mails.
     */
    SendMail mailService;

    /**
     * Builder responsible for getting the user being used.
     * @param registration 
     */
    public UserController(UserRegistration registration) {
        this.registration = registration;
        this.mailService = new SendMail();
    }

  /**
     * Add Users to the system.
     *
     * Basic: Add one User.
     * Intermediate: Add several Users.
     * Advanced: Add several Users transactionally.
     *
     * @param s
     * @return The number of added users.
     */
    public int addClient(String s) {
        List<Client> list = new LinkedList<>();
        File file = new File(s);

        try (BufferedReader br = new BufferedReader(new FileReader(file));) {

            br.lines().forEach(line -> {
                String[] param = line.split(";");
                String idUser = param[0].trim();
                String email = param[1].trim();
                int height = Integer.parseInt(param[2].trim());
                int weight = Integer.parseInt(param[3].trim());
                float cyclingAverageSpeed = Float.parseFloat(param[4].trim());
                String creditCard = (param[5].trim());
                String gender = param[6].trim();
                String password = param[7].trim();
                Client client = new Client(idUser, password, email, creditCard, height, weight, gender, cyclingAverageSpeed);
                list.add(client);
            });
//            String st;
//            while ((st = br.readLine()) != null) {
//                String[] param = st.split(";");
//                String idUser = param[0].trim();
//                String email = param[1].trim();
//                int height = Integer.parseInt(param[2].trim());
//                int weight = Integer.parseInt(param[3].trim());
//                float cyclingAverageSpeed = Float.parseFloat(param[4].trim());
//                String creditCard = (param[5].trim());
//                String gender = param[6].trim();
//                String password = param[7].trim();
//                Client client = new Client(idUser, password, email, creditCard, height, weight, gender, cyclingAverageSpeed);
//                list.add(client);
//            }
        } catch (IOException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            registration.addClientBatch(list);
        } catch (Exception ex) {
            return 0;
        }
        return list.size();
    }

 /**
     * Register a user on the system.
     *
     * @param idUser
     * @param email User's email.
     * @param password User's desired password.
     * @param visaCardNumber User's Visa Card number.
     * @param height User's height in cm.
     * @param weight User's weight in kg.
     * @param gender User's gender in text.
     * @param cyclingAverageSpeed
     * @return Return 1 if a user is successfully registered.
     */
    public int registerUser(String idUser,String email, String password, String visaCardNumber, int height, int weight, String gender,float cyclingAverageSpeed) {

        Client c = new Client(idUser, password,email, visaCardNumber, height, weight, gender, cyclingAverageSpeed);
        try {
            registration.addClient(c);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }

}
