package lapr.project.data;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.model.Client;
import oracle.jdbc.OracleTypes;

/**
 *
 *
 */
public class UserRegistration extends DataHandler {

    /**
     * Returns the regist of clients in the database.
     *
     * @param idUser
     * @return Clients object from idUser specified or null, if that regist
     * doesn't exist.
     * @throws SQLException
     */
    public Client getClient(String idUser) throws SQLException {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getClient(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, idUser);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String password = rSet.getString(2);
                String email = rSet.getString(3);
                String creditCard = rSet.getString(4);
                int height = rSet.getInt(5);
                int weight = rSet.getInt(6);
                String gender = rSet.getString(7);
                float cyclingAverageSpeed = rSet.getFloat(8);
                return new Client(idUser, password, email, creditCard, height, weight, gender, cyclingAverageSpeed);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Client with ID:" + idUser);
    }

    /**
     * Adds Client in the Batch.
     *
     * @param list
     * @throws SQLException
     * @throws IOException
     */
    public void addClientBatch(List<Client> list) throws SQLException, IOException {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addClient(?,?,?,?,?,?,?,?) }")) {

            InitializeDataHandler.dh.getConnection().setAutoCommit(false);

            for (Client p : list) {
                callStmt.setString(1, p.getIdUser());
                callStmt.setString(2, p.getPassword());
                callStmt.setString(3, p.getEmail());
                callStmt.setString(4, p.getCreditCard());
                callStmt.setInt(5, p.getHeight());
                callStmt.setInt(6, p.getWeight());
                callStmt.setString(7, p.getGender());
                callStmt.setFloat(8, p.getCyclingAverageSpeed());
                callStmt.addBatch();
            }
            callStmt.executeBatch();
            getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            getConnection().rollback();
            throw new IOException("Error in adding Users");
        }
    }

    /**
     * Adds a client.
     *
     * @param client
     * @throws SQLException
     */
    public void addClient(Client client) throws SQLException {
        addClient(client.getIdUser(), client.getPassword(), client.getEmail(), client.getCreditCard(), client.getHeight(),
                client.getWeight(), client.getGender(), client.getCyclingAverageSpeed());
    }

    /**
     * Adds a client in the database.
     *
     * @param idUser
     * @param password
     * @param email
     * @param creditCard
     * @param height
     * @param weight
     * @param gender
     * @param cyclingAverageSpeed
     * @throws SQLException
     */
    private void addClient(String idUser, String password, String email, String creditCard, int height, int weight, String gender, float cyclingAverageSpeed) throws SQLException {
        openConnection();

        try (CallableStatement callStmt = getConnection().prepareCall("{ call addClient(?,?,?,?,?,?,?,?) }")) {

            callStmt.setString(1, idUser);
            callStmt.setString(2, password);
            callStmt.setString(3, email);
            callStmt.setString(4, creditCard);
            callStmt.setInt(5, height);
            callStmt.setInt(6, weight);
            callStmt.setString(7, gender);
            callStmt.setFloat(8, cyclingAverageSpeed);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the regist of lock vehicle from park id in the database.
     *
     * @param vehicleDescription
     * @param parkIdentification
     * @return Lock Vehicle Park ID object from vehicleDescription and
     * parkIdentification specified or null, if that regist doesn't exist.
     */
    public long lockVehicleParkID(String vehicleDescription, String parkIdentification) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call lockVehicleID(?,?) }")) {

            callStmt.setString(1, vehicleDescription);
            callStmt.setString(2, parkIdentification);

            callStmt.execute();
            return System.currentTimeMillis();

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Vehicle with ID:" + vehicleDescription);
    }

    /**
     * Returns the regist of lock vehicles in the database.
     *
     * @param vehicleDescription
     * @param parkLatitudeInDegrees
     * @param parkLongitudeInDegrees
     * @return Lock Vehicle object from vehicleDescription,parkLatitudeInDegrees
     * and parkLongitudeInDegrees specified or null, if that regist doesn't
     * exist.
     */
    public long lockVehicle(String vehicleDescription, double parkLatitudeInDegrees,
            double parkLongitudeInDegrees) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call lockVehicleCoord(?,?,?,?) }")) {

            callStmt.setString(1, vehicleDescription);
            callStmt.setDouble(2, parkLatitudeInDegrees);
            callStmt.setDouble(3, parkLongitudeInDegrees);
            callStmt.setInt(4, 0);
            callStmt.execute();
            return System.currentTimeMillis();

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Vehicle with ID:" + vehicleDescription);
    }

    /**
     * Returns the regist of client emails in the database.
     *
     * @param idUser
     * @return Client Email object from idUser specified or null, if that regist
     * doesn't exist.
     */
    public String getClientEmail(String idUser) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getEmail(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);

            callStmt.setString(2, idUser);

            callStmt.execute();

            return callStmt.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Client with ID:" + idUser);

    }

    /**
     * Updates points in database.
     *
     * @param idUser
     * @throws SQLException
     */
    public void updatePoints(String idUser) throws SQLException {
        int elevationA = 0;
        int elevationB = 0;
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPointsOfTrip(?) }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setString(2, idUser);
            callStmt.execute();
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                elevationA = rSet.getInt(1);
                elevationB = rSet.getInt(2);
            }
            try (CallableStatement callStmt2 = getConnection().prepareCall("{ call addPoints(?,?,?) }")) {
                callStmt2.setString(1, idUser);
                callStmt2.setInt(2, elevationA);
                callStmt2.setInt(3, elevationB);
                callStmt2.execute();
            } catch (SQLException ex) {
                Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Adds Invoice Line in the database.
     *
     * @param username
     * @param month
     * @param year
     * @param cost
     */
    public void addInvoiceLine(String username, int month, int year, float cost) {
        openConnection();
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ call addInvoiceLine(?,?,?,?) }");

            callStmt.setString(1, username);
            callStmt.setInt(2, month);
            callStmt.setInt(3, year);
            callStmt.setFloat(4, cost);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (callStmt != null) {
                closeAll();
            }
        }
    }

    /**
     * Returns the regist of monthly costs from a user in the database.
     *
     * @param username
     * @param month
     * @param year
     * @return Monthly Cost from User object from username, month and year
     * specified or null, if that regist doesn't exist.
     */
    public float getMonthlyCostFromUser(String username, int month, int year) {
        openConnection();
        CallableStatement callStmt = null;
        try {
            callStmt = getConnection().prepareCall("{ ? =  call getMonthlyCostFromUser(?,?,?) }");

            callStmt.registerOutParameter(1, OracleTypes.FLOAT);

            callStmt.setString(2, username);
            callStmt.setInt(3, month);
            callStmt.setInt(4, year);

            callStmt.execute();

            return callStmt.getFloat(1);

        } catch (SQLException ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (callStmt != null) {
                closeAll();
            }
        }
        throw new IllegalArgumentException("No Client with ID:" + username);
    }

    /**
     * Returns the regist of unlock vehicles park id in the database.
     *
     * @param vehicleDescription
     * @return Unlock Vehicle Park ID object from vehicleDescription specified
     * or null, if that regist doesn't exist.
     */
    public long unlockVehicleParkID(String vehicleDescription) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call unlockVehicleID(?) }")) {

            callStmt.setString(1, vehicleDescription);

            callStmt.execute();
            return System.currentTimeMillis();

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Vehicle with ID:" + vehicleDescription);
    }

    /**
     * Returns the regist of unlock any scooters at park in the database.
     *
     * @param parkIdentification
     * @return Unlock any Scooters at Park object from parkIdentification
     * specified or null, if that regist doesn't exist.
     */
    public String unlockAnyEscootereAtPark(String parkIdentification) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getUnlockScooterWithMaxCapacity(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);

            callStmt.setString(2, parkIdentification);

            callStmt.execute();

            return callStmt.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException("No Park with ID:" + parkIdentification);
        }
    }

    /**
     * Returns the regist of points from user in the database.
     *
     * @param username
     * @return Points from User object from username specified or null, if that
     * regist doesn't exist.
     */
    public int getPointsFromUser(String username) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPointsFromUser(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);

            callStmt.setString(2, username);

            callStmt.execute();

            return callStmt.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Client with ID:" + username);

    }

    /**
     * Sets the points in the database.
     *
     * @param username
     * @param points
     */
    public void setPoints(String username, int points) {
        openConnection();

        try (CallableStatement callStmt = getConnection().prepareCall("{ call setPoints(?,?) }")) {

            callStmt.setString(1, username);
            callStmt.setInt(2, points);

            callStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the regist of points from user by month in the database.
     *
     * @param username
     * @param month
     * @param year
     * @return Points from User by Month object from username, month and year
     * specified or null, if that regist doesn't exist.
     */
    public int getPointsFromUserByMonth(String username, int month, int year) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPointsFromUserByMonth(?,?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);

            callStmt.setString(2, username);
            callStmt.setInt(3, month);
            callStmt.setInt(4, year);
            callStmt.execute();

            return callStmt.getInt(1);

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Client with ID:" + username);

    }

}
