/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import lapr.project.data.TripRegistration;
import lapr.project.data.UserRegistration;
import lapr.project.data.InvoiceTripReport;
import lapr.project.data.PointsTripReport;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 * @author Rafael
 */
public class InvoiceControllerTest {

    /**
     * Test of getInvoiceForMonth method, of class InvoiceController.
     */
    @Test
    public void testGetInvoiceForMonth() {
        System.out.println("getInvoiceForMonth");
        int month = 1;
        int year = 2020;
        String username = "idUser1";
        String outputPath = "InvoiceTest";
        UserRegistration ur = mock(UserRegistration.class);
        TripRegistration tr = mock(TripRegistration.class);
        InvoiceController controller = new InvoiceController(ur, tr);
        double expResult = 39.0;
        when(ur.getPointsFromUserByMonth(username, month, year)).thenReturn(20);
        when(ur.getMonthlyCostFromUser(username, month, year)).thenReturn(40f);
        when(ur.getPointsFromUser(username)).thenReturn(10);
        List<InvoiceTripReport> list = new ArrayList<>();
        Calendar unlock = Calendar.getInstance();
        unlock.set(2020, 0, 5, 18, 40);
        Calendar lock = Calendar.getInstance();
        lock.set(2020, 0, 5, 19, 40);
        Calendar unlock2 = Calendar.getInstance();
        unlock.set(2020, 0, 10, 18, 40);
        Calendar lock2 = Calendar.getInstance();
        lock.set(2020, 0, 10, 19, 40);

        InvoiceTripReport a = new InvoiceTripReport(21, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 30f);
        InvoiceTripReport b = new InvoiceTripReport(22, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 50f);

        list.add(a);
        list.add(b);

        when(tr.getUserTripReportByMonth(username, month, year)).thenReturn(list);

        assertEquals(expResult, controller.getInvoiceForMonth(month, username, outputPath), 0.0);

    }

    @Test
    public void testGetInvoiceForMonthEmptyFile() {
        System.out.println("getInvoiceForMonth");
        int month = 1;
        int year = 2020;
        String username = "idUser1";
        String outputPath = "";
        UserRegistration ur = mock(UserRegistration.class);
        TripRegistration tr = mock(TripRegistration.class);
        InvoiceController controller = new InvoiceController(ur, tr);
        double expResult = 0.0;
        when(ur.getPointsFromUserByMonth(username, month, year)).thenReturn(20);
        when(ur.getMonthlyCostFromUser(username, month, year)).thenReturn(40f);
        when(ur.getPointsFromUser(username)).thenReturn(10);
        List<InvoiceTripReport> list = new ArrayList<>();
        Calendar unlock = Calendar.getInstance();
        unlock.set(2020, 0, 5, 18, 40);
        Calendar lock = Calendar.getInstance();
        lock.set(2020, 0, 5, 19, 40);
        Calendar unlock2 = Calendar.getInstance();
        unlock.set(2020, 0, 10, 18, 40);
        Calendar lock2 = Calendar.getInstance();
        lock.set(2020, 0, 10, 19, 40);

        InvoiceTripReport a = new InvoiceTripReport(21, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 30f);
        InvoiceTripReport b = new InvoiceTripReport(22, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 50f);

        list.add(a);
        list.add(b);

        when(tr.getUserTripReportByMonth(username, month, year)).thenReturn(list);

        assertEquals(expResult, controller.getInvoiceForMonth(month, username, outputPath), 0.0);

    }

    @Test
    public void testGetInvoiceForMonthCurrentCostLessThanZero() {
        System.out.println("getInvoiceForMonth");
        int month = 1;
        int year = 2020;
        String username = "idUser1";
        String outputPath = "InvoiceTestZero";
        UserRegistration ur = mock(UserRegistration.class);
        TripRegistration tr = mock(TripRegistration.class);
        InvoiceController controller = new InvoiceController(ur, tr);
        double expResult = 0.0;
        when(ur.getPointsFromUserByMonth(username, month, year)).thenReturn(20);
        when(ur.getMonthlyCostFromUser(username, month, year)).thenReturn(0f);
        when(ur.getPointsFromUser(username)).thenReturn(10);
        List<InvoiceTripReport> list = new ArrayList<>();
        Calendar unlock = Calendar.getInstance();
        unlock.set(2020, 0, 5, 18, 40);
        Calendar lock = Calendar.getInstance();
        lock.set(2020, 0, 5, 19, 40);
        Calendar unlock2 = Calendar.getInstance();
        unlock.set(2020, 0, 10, 18, 40);
        Calendar lock2 = Calendar.getInstance();
        lock.set(2020, 0, 10, 19, 40);

        InvoiceTripReport a = new InvoiceTripReport(21, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 30f);
        InvoiceTripReport b = new InvoiceTripReport(22, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 50f);

        list.add(a);
        list.add(b);

        when(tr.getUserTripReportByMonth(username, month, year)).thenReturn(list);

        assertEquals(expResult, controller.getInvoiceForMonth(month, username, outputPath), 0.0);

    }

    @Test
    public void testGetInvoiceForMonthPointsLessThanTen() {
        System.out.println("getInvoiceForMonth");
        int month = 1;
        int year = 2020;
        String username = "idUser1";
        String outputPath = "InvoiceTestPoints";
        UserRegistration ur = mock(UserRegistration.class);
        TripRegistration tr = mock(TripRegistration.class);
        InvoiceController controller = new InvoiceController(ur, tr);
        double expResult = 40.0;
        when(ur.getPointsFromUserByMonth(username, month, year)).thenReturn(20);
        when(ur.getMonthlyCostFromUser(username, month, year)).thenReturn(40f);
        when(ur.getPointsFromUser(username)).thenReturn(5);
        List<InvoiceTripReport> list = new ArrayList<>();
        Calendar unlock = Calendar.getInstance();
        unlock.set(2020, 0, 5, 18, 40);
        Calendar lock = Calendar.getInstance();
        lock.set(2020, 0, 5, 19, 40);
        Calendar unlock2 = Calendar.getInstance();
        unlock.set(2020, 0, 10, 18, 40);
        Calendar lock2 = Calendar.getInstance();
        lock.set(2020, 0, 10, 19, 40);

        InvoiceTripReport a = new InvoiceTripReport(21, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 30f);
        InvoiceTripReport b = new InvoiceTripReport(22, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 50f);

        list.add(a);
        list.add(b);

        when(tr.getUserTripReportByMonth(username, month, year)).thenReturn(list);

        assertEquals(expResult, controller.getInvoiceForMonth(month, username, outputPath), 0.0);

    }

    @Test
    public void testGetUserCurrentDebt() {
        double expResult = 40.0;

        int month = 1;
        int year = 2020;
        String username = "idUser1";
        String outputPath = "DebtTest";
        UserRegistration ur = mock(UserRegistration.class);
        TripRegistration tr = mock(TripRegistration.class);
        InvoiceController controller = new InvoiceController(ur, tr);
        Calendar unlock = Calendar.getInstance();
        unlock.set(2020, 0, 5, 18, 40);
        Calendar lock = Calendar.getInstance();
        lock.set(2020, 0, 5, 19, 40);
        Calendar unlock2 = Calendar.getInstance();
        unlock.set(2020, 0, 10, 18, 40);
        Calendar lock2 = Calendar.getInstance();
        lock.set(2020, 0, 10, 19, 40);
        List<InvoiceTripReport> list = new ArrayList<>();

        InvoiceTripReport a = new InvoiceTripReport(21, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 30f);
        InvoiceTripReport b = new InvoiceTripReport(22, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 50f);

        list.add(a);
        list.add(b);

        when(tr.getUserTripReportByMonth(username, month, year)).thenReturn(list);
        when(ur.getMonthlyCostFromUser(username, month, year)).thenReturn(40f);
        assertEquals(expResult, controller.getUserCurrentDebt(username, outputPath));

    }

    @Test
    public void testGetUserCurrentDebtEmptyFile() {
        double expResult = 0.0;

        int month = 1;
        int year = 2020;
        String username = "idUser1";
        String outputPath = "";
        UserRegistration ur = mock(UserRegistration.class);
        TripRegistration tr = mock(TripRegistration.class);
        InvoiceController controller = new InvoiceController(ur, tr);
        Calendar unlock = Calendar.getInstance();
        unlock.set(2020, 0, 5, 18, 40);
        Calendar lock = Calendar.getInstance();
        lock.set(2020, 0, 5, 19, 40);
        Calendar unlock2 = Calendar.getInstance();
        unlock.set(2020, 0, 10, 18, 40);
        Calendar lock2 = Calendar.getInstance();
        lock.set(2020, 0, 10, 19, 40);
        List<InvoiceTripReport> list = new ArrayList<>();

        InvoiceTripReport a = new InvoiceTripReport(21, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 30f);
        InvoiceTripReport b = new InvoiceTripReport(22, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 50f);

        list.add(a);
        list.add(b);

        when(tr.getUserTripReportByMonth(username, month, year)).thenReturn(list);
        when(ur.getMonthlyCostFromUser(username, month, year)).thenReturn(40f);
        assertEquals(expResult, controller.getUserCurrentDebt(username, outputPath));

    }

    @Test
    public void testGetUserCurrentPoints() {
        double expResult = 20.0;

        int month = 1;
        int year = 2020;
        String username = "idUser1";
        String outputPath = "DebtTest";
        UserRegistration ur = mock(UserRegistration.class);
        TripRegistration tr = mock(TripRegistration.class);
        InvoiceController controller = new InvoiceController(ur, tr);
        Calendar unlock = Calendar.getInstance();
        unlock.set(2020, 0, 5, 18, 40);
        Calendar lock = Calendar.getInstance();
        lock.set(2020, 0, 5, 19, 40);
        Calendar unlock2 = Calendar.getInstance();
        unlock.set(2020, 0, 10, 18, 40);
        Calendar lock2 = Calendar.getInstance();
        lock.set(2020, 0, 10, 19, 40);
        List<PointsTripReport> list = new ArrayList<>();

        PointsTripReport a = new PointsTripReport(21, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 20);
        PointsTripReport b = new PointsTripReport(22, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 20);

        list.add(a);
        list.add(b);

        when(tr.getUserTripReportByMonthPoints(username, month, year)).thenReturn(list);
        when(ur.getPointsFromUser(username)).thenReturn(20);
        assertEquals(expResult, controller.getUserCurrentPoints(username, outputPath));

    }
    
    @Test
    public void testGetUserCurrentPointsEmptyFile() {
        double expResult = 0.0;

        int month = 1;
        int year = 2020;
        String username = "idUser1";
        String outputPath = "";
        UserRegistration ur = mock(UserRegistration.class);
        TripRegistration tr = mock(TripRegistration.class);
        InvoiceController controller = new InvoiceController(ur, tr);
        Calendar unlock = Calendar.getInstance();
        unlock.set(2020, 0, 5, 18, 40);
        Calendar lock = Calendar.getInstance();
        lock.set(2020, 0, 5, 19, 40);
        Calendar unlock2 = Calendar.getInstance();
        unlock.set(2020, 0, 10, 18, 40);
        Calendar lock2 = Calendar.getInstance();
        lock.set(2020, 0, 10, 19, 40);
        List<PointsTripReport> list = new ArrayList<>();

        PointsTripReport a = new PointsTripReport(21, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 20);
        PointsTripReport b = new PointsTripReport(22, "S001", "idUser1", 47.15227, 55.60929, 0, 22.15227, 55.30929, 0, unlock2.getTime(), lock2.getTime(), 20);

        list.add(a);
        list.add(b);

        when(tr.getUserTripReportByMonthPoints(username, month, year)).thenReturn(list);
        when(ur.getPointsFromUser(username)).thenReturn(20);
        assertEquals(expResult, controller.getUserCurrentPoints(username, outputPath));

    }
}
