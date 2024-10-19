package lapr.project.utils;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Rafael
 */
public class SendMailTest {

    public SendMailTest() {
    }

    /**
     * Test of emailBuilder method, of class SendMail.
     */
    @Test
    public void testEmailBuilder() {
        SendMail mailService = new SendMail();
        System.out.println("emailBuilder");
        String toEmail = "rafaelbarbarroxa@gmail.com";
        String vehicleDescription = "B001";
        String parkIdentification = "P001";
        String username = "Rafa";
        boolean expResult = true;
        boolean result = mailService.emailBuilder(toEmail, vehicleDescription, parkIdentification, username);
        assertEquals(expResult, result);
    }

    @Test
    public void testEmailBuilder2() {
        SendMail mailService = new SendMail();
        System.out.println("emailBuilder");
        String toEmail = null;
        String vehicleDescription = null;
        String parkIdentification = null;
        String username = null;
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            boolean result = mailService.emailBuilder(toEmail, vehicleDescription, parkIdentification, username);
        });
    }

    @Test
    public void testInvoiceBuilder() {
        SendMail mailService = new SendMail();
        System.out.println("emailBuilder");
        String toEmail = "rafaelbarbarroxa@gmail.com";
        String vehicleDescription = "B001";
        String parkIdentification = "P001";
        String username = "Rafa";
        boolean expResult = true;
        boolean result = mailService.invoiceBuilder(toEmail, username, 12, 2019, 20);
        assertEquals(expResult, result);
    }

    @Test
    public void testInvoiceBuilder2() {
        SendMail mailService = new SendMail();
        System.out.println("emailBuilder");
        String toEmail = null;
        String username = null;
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            boolean result = mailService.invoiceBuilder(toEmail, username, 12, 2019, 20);
        });
    }

}
