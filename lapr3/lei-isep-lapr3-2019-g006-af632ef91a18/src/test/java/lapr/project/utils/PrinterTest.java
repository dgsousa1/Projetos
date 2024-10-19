package lapr.project.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.model.Bicycle;
import lapr.project.model.Client;
import lapr.project.model.Location;
import lapr.project.model.Vehicle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 *
 */
public class PrinterTest {

    @Test
    public void comparatorTest() {
        Location l1 = new Location(1.0, 1.0, 0);
        Location l2 = new Location(1.01, 1.0, 1);
        Location l3 = new Location(2.0, 1.01, 0);
        Location l4 = new Location(1.01, 2.0, 1);
        Client client = new Client("idUser", "pass", "1181529@isep.ipp.pt", "123456789", 180, 80, "masculino", 12);
        Vehicle vehicle = new Bicycle(1, "B001", 10, l1, 1.1f, 0.3f);
        List<List<Location>> locations = new ArrayList<>();
        List<Location> list1 = new ArrayList<>();
        list1.add(l1);
        list1.add(l2);
        List<Location> list2 = new ArrayList<>();
        list2.add(l1);
        list2.add(l2);
        list2.add(l3);
        list2.add(l4);
        locations.add(list1);
        locations.add(list2);
        try {
            Printer.printPathEletric(list2, "Test", client, vehicle, 0);
        } catch (IOException ex) {
            Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        File file = new File("Test");
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String st = br.readLine();
            assertNotNull(st);
        } catch (IOException ex) {
            Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        locations.remove(list1);
        locations.remove(list2);
        list1.add(l3);
        list2.remove(l1);
        locations.add(list1);
        locations.add(list2);
        try {
            Printer.printPathEletric(list2, "Test", client, vehicle, 0);
        } catch (IOException ex) {
            Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String st = br.readLine();
            assertNotNull(st);
        } catch (IOException ex) {
            Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        locations.remove(list1);
        locations.remove(list2);
        list1.add(l3);
        list1.add(l4);
        list2.remove(l2);
        locations.add(list1);
        locations.add(list2);
        try {
            Printer.printPathEletric(list2, "Test", client, vehicle, 0);
        } catch (IOException ex) {
            Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            String st = br.readLine();
            assertNotNull(st);
        } catch (IOException ex) {
            Logger.getLogger(PrinterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
