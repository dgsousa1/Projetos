package eapli.base.machine.domain;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MachineTest {

    Machine instance;

   @Before
    public void before(){
        instance = new Machine(0007L, new MachineModel("ModelX","Excelente","Tesla"));
    }



    /**
     * Test of getSerialNumber method, of class Park.
     */
    @Test
  public void getSerialNumber() {
        Long exp = 0007L;
        assertEquals(exp, instance.getSerialNumber());
    }

    /**
     * Test of getMachineModel method, of class Park.
     */
    @Test
   public void getMachineModel() {
        assertNotNull(instance.getMachineModel());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSerialNumber(){
        Machine m = new Machine(null, new MachineModel("ModelX","Excelente","Tesla"));
        };


    @Test(expected =IllegalArgumentException.class)
    public void testInvalidMachineModel(){
            Machine m = new Machine(0007L,null);
    };


}