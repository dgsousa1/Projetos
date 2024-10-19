package lapr.project.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * https://www.javatpoint.com/example-of-sending-email-using-java-mail-api-through-gmail-server
 */
public class SendMail {

    public SendMail() {
    }

    public boolean emailBuilder(String toEmail, String vehicleDescription, String parkIdentification,
            String username) {

        String from = "ridesharingserviceg006@gmail.com";
        String pass = "1181498ABC";
        String subject = "Vehicle Locked- Ride Sharing Service";
        String message = messageBuilder(vehicleDescription, parkIdentification, username);
        return send(from, pass, toEmail, subject, message);
    }

    public boolean invoiceBuilder(String toEmail, String username, int month, int year, float cost) {

        String from = "ridesharingserviceg006@gmail.com";
        String pass = "1181498ABC";
        String subject = "Monthly Invoice- Ride Sharing Service";
        String message = invoiceMessageBuilder(username, month, year, cost);
        return send(from, pass, toEmail, subject, message);
    }

    private String messageBuilder(String vehicleDescription, String parkIdentification,
            String username) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ride Sharing Service\n\n ");

        sb.append("Hello " + username);
        sb.append("\n The vehicle " + vehicleDescription
                + " was successfully parked in: " + parkIdentification);
        sb.append("\n\n\n\n");

        sb.append("ISEP - Ride Sharing Service\n\n");

        return sb.toString();
    }

    private String invoiceMessageBuilder(String username, int month, int year, float cost) {
        StringBuilder sb = new StringBuilder();
        sb.append("Ride Sharing Service\n\n ");

        sb.append("Hello " + username);
        sb.append("\n At the date of " + "5/" + month + "/" + year + "\n You must pay " + cost + "€");

        sb.append("\n\n\n\n");

        sb.append("ISEP - Ride Sharing Service\n\n");

        return sb.toString();
    }

    private boolean send(String from, String password, String to, String sub, String msg) {
        //Get properties object    
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.checkserveridentity", "true");
        //get Session   
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        //compose message    
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message  
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
