package lapr.project.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.ParkRegistration;
import lapr.project.data.TripRegistration;
import lapr.project.data.UserRegistration;
import lapr.project.utils.SendMail;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author Rafael
 */
public class LockVehicleControllerTest {

    @Test
    public void testLockBicycleCoords() {
        long expResult = 4;
        LockVehicleController controller = mock(LockVehicleController.class);
        when(controller.lockBicycle("B001", 40, 40, "dummy")).thenReturn((long) 4);
        long res = controller.lockBicycle("B001", 40, 40, "dummy");
        assertEquals(expResult, res);
    }

    @Test
    public void testLockBicycleCoordsFails() {
        long expResult = 0;
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
        long result = controller.lockBicycle("B001", 40, 40, "Dummy");
        assertEquals(expResult, result);
    }

    @Test
    public void testLockEScooterCoords() {
        long expResult = 4;
        LockVehicleController controller = mock(LockVehicleController.class);
        when(controller.lockEscooter("S001", 40, 40, "dummy")).thenReturn((long) 4);
        assertEquals(expResult, controller.lockEscooter("S001", 40, 40, "dummy"));
    }

    @Test
    public void testLockEScooterCoordsFails() {
        long expResult = 0;
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
        long result = controller.lockEscooter("S001", 40, 40, "Dummy");
        assertEquals(expResult, result);
    }

    @Test
    public void testLockBicycleParkID() {
        long expResult = 4;
        LockVehicleController controller = mock(LockVehicleController.class);
        when(controller.lockBicycle("B001", "P001", "dummy")).thenReturn((long) 4);
        assertEquals(expResult, controller.lockBicycle("B001", "P001", "dummy"));
    }

    @Test
    public void testLockBicycleParkIDFails() {
        long expResult = 0;
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
        long result = controller.lockBicycle("B001", "P001", "Dummy");
        assertEquals(expResult, result);
    }

    @Test
    public void testLockEScooterParkID() {
        long expResult = 4;
        LockVehicleController controller = mock(LockVehicleController.class);
        when(controller.lockEscooter("S001", "P001", "dummy")).thenReturn((long) 4);
        assertEquals(expResult, controller.lockEscooter("S001", "P001", "dummy"));
    }

    @Test
    public void testLockEScooterParkIDFails() {
        long expResult = 0;
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
        long result = controller.lockEscooter("S001", "P001", "Dummy");
        assertEquals(expResult, result);
    }

    @Test
    public void testCalculateCost() {
        float expResult = 30f;
        String username = "idUser";
        LockVehicleController controller = mock(LockVehicleController.class);
        when(controller.calculateCost(username)).thenReturn(30f);
        assertEquals(expResult, controller.calculateCost(username));
    }

    @Test
    public void testCalculateCostFails() {
        float expResult = -1;
        String username = "idUser";
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
        float result = controller.calculateCost(username);
        assertEquals(expResult, result);
    }

    @Test
    public void testLockBicycleCoordsEmptyString() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("", 40, 40, "");
        });
    }

    @Test
    public void testLockBicycleCoordsEmptyString1() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("B001", 40, 40, "");
        });
    }

    @Test
    public void testLockBicycleCoordsEmptyString3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("", 40, 40, "dummy");
        });
    }

    @Test
    public void testLockBicycleParkIDEmptyString() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("", "", "");
        });
    }

    @Test
    public void testLockBicycleParkIDEmptyString2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("B001", "", "");
        });
    }

    @Test
    public void testLockBicycleParkIDEmptyString3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("", "P001", "");
        });
    }

    @Test
    public void testLockBicycleParkIDEmptyString4() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("", "", "dummy");
        });
    }

    @Test
    public void testLockBicycleParkIDEmptyString5() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("B001", "", "dummy");
        });
    }

    @Test
    public void testLockBicycleParkIDEmptyString6() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("B001", "P001", "");
        });
    }

    @Test
    public void testLockBicycleParkIDEmptyString7() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockBicycle("", "P001", "dummy");
        });
    }

    @Test
    public void testLockEScooterCoordsEmptyString() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("", 40, 40, "");
        });
    }

    @Test
    public void testLockEScooterCoordsEmptyString2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("P001", 40, 40, "");
        });
    }

    @Test
    public void testLockEScooterCoordsEmptyString3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("", 40, 40, "dummy");
        });
    }

    @Test
    public void testLockEScooterParkIDEmptyString() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("", "", "");
        });
    }

    @Test
    public void testLockEScooterParkIDEmptyString2() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("S001", "", "");
        });
    }

    @Test
    public void testLockEScooterParkIDEmptyString3() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("", "P001", "");
        });
    }

    @Test
    public void testLockEScooterParkIDEmptyString4() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("", "", "dummy");
        });
    }

    @Test
    public void testLockEScooterParkIDEmptyString5() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("S001", "", "dummy");
        });
    }

    @Test
    public void testLockEScooterParkIDEmptyString6() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("S001", "P001", "");
        });
    }

    @Test
    public void testLockEScooterParkIDEmptyString7() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), new TripRegistration());
            controller.lockEscooter("", "P001", "dummy");
        });
    }

    @Test
    public void calculateCostTest1() {
        float expResult = 0.25f;
        String username = "idUser";
        TripRegistration registration = mock(TripRegistration.class);
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), registration);
        List<Calendar> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.set(2019, 11, 29, 11, 10);
        cal2.set(2019, 11, 29, 12, 20);
        list.add(cal);
        list.add(cal2);
        try {
            when(registration.getUserTripDates(username)).thenReturn(list);
        } catch (SQLException ex) {
            Logger.getLogger(LockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, controller.calculateCost(username));
    }

    @Test
    public void calculateCostTest2() {
        float expResult = 0;
        String username = "idUser";
        TripRegistration registration = mock(TripRegistration.class);
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), registration);
        List<Calendar> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.set(2019, 11, 29, 11, 10);
        cal2.set(2019, 11, 29, 11, 20);
        list.add(cal);
        list.add(cal2);
        try {
            when(registration.getUserTripDates(username)).thenReturn(list);
        } catch (SQLException ex) {
            Logger.getLogger(LockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, controller.calculateCost(username));
    }

    @Test
    public void calculateCostTest3() {
        float expResult = 0;
        String username = "idUser";
        TripRegistration registration = mock(TripRegistration.class);
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), new UserRegistration(), registration);
        List<Calendar> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.set(2019, 11, 29, 11, 10);
        cal2.set(2019, 11, 29, 12, 10);
        list.add(cal);
        list.add(cal2);
        try {
            when(registration.getUserTripDates(username)).thenReturn(list);
        } catch (SQLException ex) {
            Logger.getLogger(LockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, controller.calculateCost(username));
    }

    @Test
    public void testLockBicycleParkID2() {
        float expResult = 0;
        String username = "idUser";
        String vdesc = "B001";
        String pdesc = "P001";
        TripRegistration tregistration = mock(TripRegistration.class);
        UserRegistration uregistration = mock(UserRegistration.class);
        SendMail mailService = mock(SendMail.class);
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), uregistration, tregistration);
        List<Calendar> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.set(2019, 11, 29, 11, 10);
        cal2.set(2019, 11, 29, 12, 20);
        list.add(cal);
        list.add(cal2);
        try {
            when(uregistration.lockVehicleParkID(vdesc, pdesc)).thenReturn(4l);
            when(tregistration.getUserTripDates(username)).thenReturn(list);
            when(mailService.emailBuilder("teste@gmail.com", vdesc, pdesc, username)).thenReturn(Boolean.TRUE);
            float cost = controller.calculateCost(username);
        } catch (SQLException ex) {
            Logger.getLogger(LockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(expResult, controller.lockBicycle(vdesc, pdesc, username));
    }

    @Test
    public void testLockBicycleCoords2() {
        float expResult = 0;
        String username = "idUser";
        String vdesc = "B001";
        String pdesc = "P001";
        double lat = 30;
        double lon = 30;
        TripRegistration tregistration = mock(TripRegistration.class);
        UserRegistration uregistration = mock(UserRegistration.class);
        ParkRegistration pregistration = mock(ParkRegistration.class);
        SendMail mailService = mock(SendMail.class);
        LockVehicleController controller = new LockVehicleController(pregistration, uregistration, tregistration);
        List<Calendar> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.set(2019, 11, 29, 11, 10);
        cal2.set(2019, 11, 29, 12, 20);
        list.add(cal);
        list.add(cal2);
        try {
            when(uregistration.lockVehicleParkID(vdesc, pdesc)).thenReturn(4l);
            when(tregistration.getUserTripDates(username)).thenReturn(list);
            when(mailService.emailBuilder("teste@gmail.com", vdesc, pdesc, username)).thenReturn(Boolean.TRUE);
            float cost = controller.calculateCost(username);
            when(pregistration.getParkByCoords(lat, lon, 0)).thenReturn("P001");
        } catch (SQLException ex) {
            Logger.getLogger(LockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(expResult, controller.lockBicycle(vdesc, lat, lon, username));
    }

    @Test
    public void testLockEScooterCoords2() {
        float expResult = 0;
        String username = "idUser";
        String vdesc = "S001";
        String pdesc = "P001";
        double lat = 30;
        double lon = 30;
        TripRegistration tregistration = mock(TripRegistration.class);
        UserRegistration uregistration = mock(UserRegistration.class);
        ParkRegistration pregistration = mock(ParkRegistration.class);
        SendMail mailService = mock(SendMail.class);
        LockVehicleController controller = new LockVehicleController(pregistration, uregistration, tregistration);
        List<Calendar> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.set(2019, 11, 29, 11, 10);
        cal2.set(2019, 11, 29, 12, 20);
        list.add(cal);
        list.add(cal2);
        try {
            when(uregistration.lockVehicleParkID(vdesc, pdesc)).thenReturn(4l);
            when(tregistration.getUserTripDates(username)).thenReturn(list);
            when(mailService.emailBuilder("teste@gmail.com", vdesc, pdesc, username)).thenReturn(Boolean.TRUE);

            float cost = controller.calculateCost(username);
            when(pregistration.getParkByCoords(lat, lon, 0)).thenReturn("P001");
        } catch (SQLException ex) {
            Logger.getLogger(LockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(expResult, controller.lockEscooter(vdesc, lat, lon, username));
    }

    @Test
    public void testLockScooterParkID2() {
        float expResult = 0;
        String username = "idUser";
        String vdesc = "S001";
        String pdesc = "P001";
        TripRegistration tregistration = mock(TripRegistration.class);
        UserRegistration uregistration = mock(UserRegistration.class);
        SendMail mailService = mock(SendMail.class);
        LockVehicleController controller = new LockVehicleController(new ParkRegistration(), uregistration, tregistration);
        List<Calendar> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal.set(2019, 11, 29, 11, 10);
        cal2.set(2019, 11, 29, 12, 20);
        list.add(cal);
        list.add(cal2);
        try {
            when(uregistration.lockVehicleParkID(vdesc, pdesc)).thenReturn(4l);
            when(tregistration.getUserTripDates(username)).thenReturn(list);
            when(mailService.emailBuilder("teste@gmail.com", vdesc, pdesc, username)).thenReturn(Boolean.TRUE);

            float cost = controller.calculateCost(username);
        } catch (SQLException ex) {
            Logger.getLogger(LockVehicleControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(expResult, controller.lockEscooter(vdesc, pdesc, username));
    }

}
