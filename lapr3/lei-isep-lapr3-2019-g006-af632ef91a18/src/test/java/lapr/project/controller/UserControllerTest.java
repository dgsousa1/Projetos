/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import lapr.project.data.UserRegistration;
import lapr.project.utils.SendMail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 * @author Rafael Crista
 */
public class UserControllerTest {

    UserController controller;

    /**
     * Test of addClient method, of class UserController.
     */
    @Test
    public void testAddClientFail() {
        int expectedResult = 0;
        controller = new UserController(new UserRegistration());
        int result = controller.addClient("UserTest");
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAddClientNoFile() {
        int expResult = 0;
        controller = new UserController(new UserRegistration());
        int result = controller.addClient("fail");
        assertEquals(expResult, result);
    }

    @Test
    public void testAddClientEmptyFile() {
        int expResult = 0;
        controller = new UserController(new UserRegistration());
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            int result = controller.addClient("EmptyTestFail");
            assertEquals(expResult, result);
        });
    }

    @Test
    public void testAddClient() {
        int expResult = 2;
        UserRegistration registration = mock(UserRegistration.class);
        controller = new UserController(registration);
        int result = controller.addClient("UserTest");
        assertEquals(expResult, result);
    }

    @Test
    public void testRegisterUserNoFile() {
        int expResult = 0;
        controller = new UserController(new UserRegistration());
        int result = controller.registerUser("joao","pass","1181529@isep.ipp.pt","123456789", 80, 180, "masculino",5);
        assertEquals(expResult, result);
    }

    @Test
    public void testRegisterUser() {
        int expResult = 1;
        UserRegistration registration = mock(UserRegistration.class);
        controller = new UserController(registration);
        int result = controller.registerUser("joao","pass","1181529@isep.ipp.pt","123456789", 80, 180, "masculino",5);
        assertEquals(expResult, result);
    }
    
}
