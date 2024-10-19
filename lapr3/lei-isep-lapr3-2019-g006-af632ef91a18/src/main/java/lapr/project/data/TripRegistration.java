package lapr.project.data;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import lapr.project.model.Location;
import java.util.logging.Logger;
import lapr.project.model.TripReport;
import lapr.project.model.TripRequest;
import oracle.jdbc.OracleTypes;

public class TripRegistration extends DataHandler {

    /**
     * Gets the user trip report from the database.
     *
     * @param idUser
     * @return report
     */
    public List<TripReport> getUserTripReport(String idUser) {
        openConnection();
        List<TripReport> report = new ArrayList<>();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getUserTripReport(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, idUser);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            while (rSet.next()) {
                int idTrip = rSet.getInt(1);
                String idVehicle = rSet.getString(2);
                String idClient = rSet.getString(3);

                double startLatitude = rSet.getDouble(4);
                double startLongitude = rSet.getDouble(5);
                int startElevation = rSet.getInt(6);
                Location locationA = new Location(startLatitude, startLongitude, startElevation);

                double endtLatitude = rSet.getDouble(7);
                double endLongitude = rSet.getDouble(8);
                int endElevation = rSet.getInt(9);
                Location locationB = new Location(endtLatitude, endLongitude, endElevation);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                String unlockingDate = formatter.format(rSet.getDate(10));
                String lockingDate = formatter.format(rSet.getDate(11));

                TripReport trip = new TripReport(idVehicle, idUser, locationA, locationB, unlockingDate, lockingDate);
                report.add(trip);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TripRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }

    /**
     * Saves a trip from the database.
     *
     * @param trip
     * @throws SQLException
     */
    public void saveTrip(TripRequest trip) throws SQLException {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addTrip(?,?,?,?,?,?,?) }")) {

            callStmt.setString(1, trip.getVehicle().getIdVehicle());
            callStmt.setString(2, trip.getClient().getIdUser());
            callStmt.setDouble(3, trip.getOrigin().getLatitude());
            callStmt.setDouble(4, trip.getOrigin().getLongitude());
            callStmt.setDouble(5, trip.getDestination().getLatitude());
            callStmt.setDouble(6, trip.getDestination().getLongitude());
            callStmt.setTime(7, new Time(trip.getInitialDate().toEpochSecond(ZoneOffset.UTC)));

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TripRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the user trip dates.
     *
     * @param idUser
     * @return list
     * @throws SQLException
     */
    public List<Calendar> getUserTripDates(String idUser) throws SQLException {
        openConnection();
        List<Calendar> list = new ArrayList<>();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getTripDate(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, idUser);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            while (rSet.next()) {

                Date unlockingDate = rSet.getDate(1);
                Date lockingDate = rSet.getDate(2);
                Calendar unlockingCal = Calendar.getInstance();
                unlockingCal.setTime(unlockingDate);
                Calendar lockingCal = Calendar.getInstance();
                lockingCal.setTime(lockingDate);
                list.add(unlockingCal);
                list.add(lockingCal);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TripRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Gets the user trip report by month.
     *
     * @param idUser
     * @param month
     * @param year
     * @return report
     */
    public List<InvoiceTripReport> getUserTripReportByMonth(String idUser, int month, int year) {
        openConnection();
        List<InvoiceTripReport> report = new ArrayList<>();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getUserTripReportByMonth(?,?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, idUser);
            callStmt.setInt(3, month);
            callStmt.setInt(4, year);
            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            while (rSet.next()) {
                int idTrip = rSet.getInt(1);
                String idVehicle = rSet.getString(2);
                String idClient = rSet.getString(3);

                double startLatitude = rSet.getDouble(4);
                double startLongitude = rSet.getDouble(5);
                int startElevation = rSet.getInt(6);

                double endtLatitude = rSet.getDouble(7);
                double endLongitude = rSet.getDouble(8);
                int endElevation = rSet.getInt(9);

                Date unlockingDate = rSet.getDate(10);
                Date lockingDate = rSet.getDate(11);
                float cost = rSet.getFloat(12);

                InvoiceTripReport itr = new InvoiceTripReport(idTrip, idVehicle, idClient, startLatitude, startLongitude, startElevation, endtLatitude, endLongitude, endElevation, unlockingDate, lockingDate, cost);

                report.add(itr);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TripRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }

    /**
     * Gets the user points report by month.
     *
     * @param idUser
     * @param month
     * @param year
     * @return report
     */
    public List<PointsTripReport> getUserTripReportByMonthPoints(String idUser, int month, int year) {
        openConnection();
        List<PointsTripReport> report = new ArrayList<>();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getUserTripReportByMonthPoints(?,?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, idUser);
            callStmt.setInt(3, month);
            callStmt.setInt(4, year);
            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            while (rSet.next()) {
                int idTrip = rSet.getInt(1);
                String idVehicle = rSet.getString(2);
                String idClient = rSet.getString(3);

                double startLatitude = rSet.getDouble(4);
                double startLongitude = rSet.getDouble(5);
                int startElevation = rSet.getInt(6);

                double endtLatitude = rSet.getDouble(7);
                double endLongitude = rSet.getDouble(8);
                int endElevation = rSet.getInt(9);

                Date unlockingDate = rSet.getDate(10);
                Date lockingDate = rSet.getDate(11);
                int points = rSet.getInt(12);

                PointsTripReport ptr = new PointsTripReport(idTrip, idVehicle, idClient, startLatitude, startLongitude, startElevation, endtLatitude, endLongitude, endElevation, unlockingDate, lockingDate, points);
                report.add(ptr);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TripRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }

}
