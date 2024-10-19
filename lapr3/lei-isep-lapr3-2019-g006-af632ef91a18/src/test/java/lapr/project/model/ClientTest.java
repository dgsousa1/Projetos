/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.mockito.Mockito.*;

/**
 *
 * @author Rafael Crista
 */
public class ClientTest {

    Client instance;

    @BeforeEach
    public void before() {
        instance = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
    }

    /**
     * Test of getEmail method, of class Client.
     */
    @Test
    public void testGetEmail() {
        String exp = "1000000@isep.ipp.pt";
        assertEquals(exp, instance.getEmail());
    }

    /**
     * Test of getHeight method, of class Client.
     */
    @Test
    public void testGetHeight() {
        int exp = 180;
        assertEquals(exp, instance.getHeight());
    }

    /**
     * Test of getWeight method, of class Client.
     */
    @Test
    public void testGetWeight() {
        int exp = 80;
        assertEquals(exp, instance.getWeight());
    }

    /**
     * Test of getCyclingAverageSpeed method, of class Client.
     */
    @Test
    public void testGetCyclingAverageSpeed() {
        float exp = (float) 12.5;
        assertEquals(exp, instance.getCyclingAverageSpeed());
    }

    /**
     * Test of getCreditCard method, of class Client.
     */
    @Test
    public void testGetCreditCard() {
        String exp = "123456";
        assertEquals(exp, instance.getCreditCard());
    }

    /**
     * Test of getGender method, of class Client.
     */
    @Test
    public void testGetGender() {
        String exp = "Male";
        assertEquals(exp, instance.getGender());
    }

    /**
     * Test of getPoints method, of class Client.
     */
    @Test
    public void testGetPoints() {
        int exp = 0;
        assertEquals(exp, instance.getPoints());
    }

    @Test
    public void testAddPoints() {
        int exp = 2;
        instance.addPoints(2);
        assertEquals(exp, instance.getPoints());
    }
    
    @Test
    public void testInvalidEmail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client c = new Client("", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "Male", (float) 12.5);
        });
    }

    @Test
    public void testInvalidHeight() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client c = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 0, 80, "Male", (float) 12.5);
        });
    }

    @Test
    public void testInvalidWeight() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client c = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 0, "Male", (float) 12.5);
        });
    }

    @Test
    public void testInvalidGender() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Client c = new Client("username", "pass1234", "1000000@isep.ipp.pt", "123456", 180, 80, "", (float) 12.5);
        });
    }

}
