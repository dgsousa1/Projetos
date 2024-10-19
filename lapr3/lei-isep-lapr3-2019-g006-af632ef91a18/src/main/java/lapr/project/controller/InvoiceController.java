package lapr.project.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.InvoiceTripReport;
import lapr.project.data.PointsTripReport;
import lapr.project.data.TripRegistration;
import lapr.project.data.UserRegistration;
import lapr.project.utils.Printer;

/**
 *
 *
 */
public class InvoiceController {

    /**
     * User manager.
     */
    UserRegistration userRegistration;

    /**
     * Trip manager.
     */
    TripRegistration tripRegistration;

    /**
     * Builder responsible for getting the invoice being used.
     *
     * @param userRegistration
     * @param tripRegistration
     */
    public InvoiceController(UserRegistration userRegistration, TripRegistration tripRegistration) {
        this.userRegistration = userRegistration;
        this.tripRegistration = tripRegistration;
    }

    /**
     * Get the current invoice for the current month, for a specific user. This
     * should include all loans that were charged the user, the number of points
     * the user had before the actual month, the number of points earned during
     * the month, the number of points converted to euros.
     *
     * @param month The month of the invoice e.g. 1 for January.
     * @param username The user for which the invoice should be created.
     * @param outputPath Path to file where the invoice should be written,
     * according to file output/invoice.csv.
     * @return User debt in euros rounded to two decimal places.
     */
    public double getInvoiceForMonth(int month, String username, String outputPath) {
        Calendar calCurrentMonth = Calendar.getInstance();
        calCurrentMonth.set(Calendar.MONTH, month - 1);
        int currentYear = calCurrentMonth.get(Calendar.YEAR);
        int pointsEarnedThisMonth = userRegistration.getPointsFromUserByMonth(username, month, currentYear);

        Calendar calPreviousMonth = calCurrentMonth;
        calPreviousMonth.add(Calendar.MONTH, -1);
        int previousMonth = calPreviousMonth.get(Calendar.MONTH);
        int previousYear = calPreviousMonth.get(Calendar.YEAR);

        int previousMonthPoints = userRegistration.getPointsFromUserByMonth(username, previousMonth + 1, previousYear);

        float currentCost = userRegistration.getMonthlyCostFromUser(username, month, currentYear);
        int actualPoints = userRegistration.getPointsFromUser(username);
        int discountedPoints = 0;
        while (currentCost > 0) {
            if (actualPoints < 10) {
                break;
            }
            discountedPoints += 10;
            actualPoints -= 10;
            currentCost -= 1;
        }
        List<InvoiceTripReport> list = tripRegistration.getUserTripReportByMonth(username, month, currentYear);
        try {
            Printer.printInvoice(username, previousMonthPoints, pointsEarnedThisMonth, discountedPoints, actualPoints, currentCost, list, outputPath);
            userRegistration.setPoints(username, actualPoints);
        } catch (IOException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

        return Math.round(currentCost * 100.0) / 100.0;
    }

    /**
     * Return the current debt for the user.
     *
     * @param username The username.
     * @param outputFileName The path for the file to output the debt, according
     * to file output/balance.csv. Sort the information by unlock time in
     * ascending order (oldest to newest).
     * @return The User's current debt in euros, rounded to two decimal places.
     */
    public double getUserCurrentDebt(String username, String outputFileName) {
        Calendar calCurrentMonth = Calendar.getInstance();
        int currentMonth = 1 + calCurrentMonth.get(Calendar.MONTH);
        int currentYear = calCurrentMonth.get(Calendar.YEAR);
        List<InvoiceTripReport> list = tripRegistration.getUserTripReportByMonth(username, currentMonth, currentYear);
        try {
            Printer.printBalance(list, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        float currentCost = userRegistration.getMonthlyCostFromUser(username, currentMonth, currentYear);
        return Math.round(currentCost * 100.0) / 100.0;
    }

    /**
     * Return the current points for the user.
     *
     * @param username The user to get the points report from.
     * @param outputFileName The path for the file to output the points,
     * according to file output/points.csv. Sort the information by unlock time
     * in ascenind order (oldest to newest).
     * @return The User's current points.
     */
    public double getUserCurrentPoints(String username, String outputFileName) {
        Calendar calCurrentMonth = Calendar.getInstance();
        int currentMonth = 1 + calCurrentMonth.get(Calendar.MONTH);
        int currentYear = calCurrentMonth.get(Calendar.YEAR);
        List<PointsTripReport> list = tripRegistration.getUserTripReportByMonthPoints(username, currentMonth, currentYear);
        try {
            Printer.printPoints(list, outputFileName);
        } catch (IOException ex) {
            Logger.getLogger(InvoiceController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return userRegistration.getPointsFromUser(username);
    }
}
