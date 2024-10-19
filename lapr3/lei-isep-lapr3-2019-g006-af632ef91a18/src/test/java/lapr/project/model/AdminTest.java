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
public class AdminTest {
    
    Admin instance;
    
    @BeforeEach
    public void before(){
        instance = new Admin("admin", "password");
    }   
   
    /**
     * Test of getIdUser method, of class User.
     */
    @Test
    public void testGetIdUser() {
        String exp = "admin";
        assertEquals(exp,instance.getIdUser());
    }
    /**
     * Test of getPassword method, of class User.
     */    
    @Test
    public void testGetPassword() {
        String exp = "password";
        assertEquals(exp,instance.getPassword());
    }
    
}
