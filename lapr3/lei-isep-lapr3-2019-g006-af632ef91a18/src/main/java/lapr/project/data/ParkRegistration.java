package lapr.project.data;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.model.Client;
import lapr.project.model.Location;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.model.Vehicle;
import lapr.project.utils.Calculator;
import lapr.project.utils.Graph;
import oracle.jdbc.OracleTypes;

public class ParkRegistration extends DataHandler {

    /**
     * Returns the regist of parks in the database.
     * @param idPark
     * @return Park object from idPark specified or null, if that
     * regist doesn't exist.
     * @throws SQLException 
     */
    public Park getPark(String idPark) throws SQLException {
        /*InitializeDataHandler.dh.*/openConnection();
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call getPark(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.setString(2, idPark);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                double latitude = rSet.getDouble(2);
                double longitude = rSet.getDouble(3);
                int elevation = rSet.getInt(4);
                Location location = new Location(latitude, longitude, elevation);
                String description = rSet.getString(5);
                int maxBike = rSet.getInt(6);
                int maxScooter = rSet.getInt(7);
                float inputVoltage = rSet.getFloat(8);
                float inputCurrent = rSet.getFloat(9);
                return new Park(idPark, location, description, maxBike, maxScooter, inputVoltage, inputCurrent);
            }
        } catch (Exception ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }

        throw new IllegalArgumentException("No Park with ID:" + idPark);
    }

    /*  public void addPark(Park park) throws SQLException {
        addPark(park.getIdPark(), park.getLocation(), park.getDescription(), park.getMaxBike(), park.getMaxScooter(),
                park.getInputVoltage(), park.getInputCurrent());
    } */
    /**
     * Adds a park to the batch.
     * @param list
     * @throws IOException
     * @throws SQLException 
     */
    public void addParkBatch(List<Park> list) throws IOException, SQLException {
        //InitializeDataHandler.dh.openConnection();
        openConnection();
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ call addPark(?,?,?,?,?,?,?,?,?) }")) {
            //InitializeDataHandler.dh.getConnection().setAutoCommit(false);
            getConnection().setAutoCommit(false);

            for (Park p : list) {
                callStmt.setString(1, p.getIdPark());
                callStmt.setDouble(2, p.getLocation().getLatitude());
                callStmt.setDouble(3, p.getLocation().getLongitude());
                callStmt.setInt(4, p.getLocation().getElevation());
                callStmt.setString(5, p.getDescription());
                callStmt.setInt(6, p.getMaxBike());
                callStmt.setInt(7, p.getMaxScooter());
                callStmt.setFloat(8, p.getInputVoltage());
                callStmt.setFloat(9, p.getInputCurrent());
                callStmt.addBatch();
            }

            callStmt.executeBatch();
            //InitializeDataHandler.dh.getConnection().commit();
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, new Exception("commit ParkRegistration"));
            getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            //InitializeDataHandler.dh.getConnection().rollback();
            getConnection().rollback();
            throw new IOException("Error in adding parks");
        }
    }

    /*  private void addPark(String idPark, Location location, String description, int maxBike, int maxScooter, float inputVoltage, float inputCurrent) throws SQLException {
        InitializeDataHandler.dh.openConnection();
        CallableStatement callStmt = null;
        try {
            callStmt = InitializeDataHandler.dh.getConnection().prepareCall("{ call addPark(?,?,?,?,?,?,?,?,?) }");

            callStmt.setString(1, idPark);
            callStmt.setDouble(2, location.getLatitude());
            callStmt.setDouble(3, location.getLongitude());
            callStmt.setInt(4, location.getElevation());
            callStmt.setString(5, description);
            callStmt.setInt(6, maxBike);
            callStmt.setInt(7, maxScooter);
            callStmt.setFloat(8, inputVoltage);
            callStmt.setFloat(9, inputCurrent);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (callStmt != null) {
                closeAll();
            }
        }

    } */
    /**
     * Returns an updated park.
     * @param park
     * @return updatePark
     * @throws SQLException 
     */
    public int updatePark(Park park) throws SQLException {
        return updatePark(park.getIdPark(), park.getLocation(), park.getDescription(), park.getMaxBike(), park.getMaxScooter(),
                park.getInputVoltage(), park.getInputCurrent());
    }

    /**
     * Updates a park in the database.
     * @param idPark
     * @param location
     * @param description
     * @param maxBike
     * @param maxScooter
     * @param inputVoltage
     * @param inputCurrent
     * @return 1 if a park was updated in the database or 0 if it wasn't.
     * @throws SQLException 
     */
    private int updatePark(String idPark, Location location, String description, int maxBike,
            int maxScooter, float inputVoltage, float inputCurrent) throws SQLException {
        /*InitializeDataHandler.dh.*/openConnection();

        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ call updatePark(?,?,?,?,?,?,?,?,?) }")) {
            callStmt.setString(1, idPark);
            callStmt.setDouble(2, location.getLatitude());
            callStmt.setDouble(3, location.getLongitude());
            callStmt.setInt(4, location.getElevation());
            callStmt.setString(5, description);
            callStmt.setInt(6, maxBike);
            callStmt.setInt(7, maxScooter);
            callStmt.setFloat(8, inputVoltage);
            callStmt.setFloat(9, inputCurrent);

            callStmt.execute();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * NearestParks object from idPark specified or null, if that
     * regist doesn't exist.
     * @param v
     * @param v1
     * @param i
     * @return nearestParks
     * @throws SQLException 
     */
    public Map<Double, Park> getNearestParks(double v, double v1, int i) throws SQLException {
        List<Park> parks = new ArrayList<>();
        try {
            parks = getAllParks();
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<Double, Park> nearestParks = new TreeMap<>();

        for (Park p : parks) {
            Double distance = Calculator.calcularDistancia(v, v1, p.getLocation().getLatitude(), p.getLocation().getLongitude());
            if (distance <= i) {
                nearestParks.put(distance, p);
            }
        }
        return nearestParks;
    }

    /**
     * Get all the parks from the database.
     * @return parks
     * @throws SQLException 
     */
    public List<Park> getAllParks() throws SQLException {
        /*InitializeDataHandler.dh.*/openConnection();
        List<Park> parks = new ArrayList<>();
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call getAllParks() }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            while (rSet.next()) {
                String idPark = rSet.getString(1);
                double latitude = rSet.getDouble(2);
                double longitude = rSet.getDouble(3);
                Location location = new Location(latitude, longitude);
                String description = rSet.getString(4);
                int maxBike = rSet.getInt(5);
                int maxScooter = rSet.getInt(6);
                float inputVoltage = rSet.getFloat(7);
                float inputCurrent = rSet.getFloat(8);
                Park park = new Park(idPark, location, description, maxBike, maxScooter, inputVoltage, inputCurrent);
                parks.add(park);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parks;
    }

    /**
     * Removes a park from the database.
     * @param idPark
     * @return 1 if the park was removed from the database and 0 if it wasn't.
     * @throws SQLException 
     */
    public int removePark(String idPark) throws SQLException {
        /*InitializeDataHandler.dh.*/openConnection();
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ call removePark(?) }")) {

            callStmt.setString(1, idPark);

            callStmt.execute();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * Returns the regist of free bicycle slots of a park in the database.
     * @param idPark
     * @return Free Bicycle Slots of a Park object from idPark specified or null, if that
     * regist doesn't exist.
     * @throws SQLException 
     */
    public int getFreeBicycleSlotsAtPark(String idPark) throws SQLException {
        /*InitializeDataHandler.dh.*/openConnection();
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call checkFreeBicyclesPlaces(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, idPark);

            callStmt.execute();

            return callStmt.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Park with ID:" + idPark);
    }

    /**
     * Returns the regist of free scooters slots of a park in the database.
     * @param idPark
     * @return Free Scooter Slots of a Park object from idPark specified or null, if that
     * regist doesn't exist.
     * @throws SQLException 
     */
    public int getFreeEscooterSlotsAtPark(String idPark) throws SQLException {
        /*InitializeDataHandler.dh.*/openConnection();
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call checkFreeScooterPlaces(?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, idPark);

            callStmt.execute();
            return callStmt.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Park with ID:" + idPark);
    }
    
    /**
     * Returns the regist of free slots of a park from a loaned vehicle in the database.
     * @param username
     * @param parkIdentification
     * @return  Free Slots of a Park from a Loaned Vehicle object from username and parkIdentification specified or null, if that
     * regist doesn't exist.
     * @throws SQLException 
     */
    public int getFreeSlotsAtParkForMyLoanedVehicle(String username, String parkIdentification) throws SQLException {
        openConnection();
        try (CallableStatement callStmt =getConnection().prepareCall("{ ? = call checkFreeVehiclesPlaces(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.INTEGER);
            callStmt.setString(2, username);
            callStmt.setString(3, parkIdentification);

            callStmt.execute();
            return callStmt.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Park with ID:" + parkIdentification);
    }

    /**
     * Returns the regist of parks by coords in the database.
     * @param latitude
     * @param longitude
     * @param elevation
     * @return Parks by coords object from latitude,longitude and elevation specified or null, if that
     * regist doesn't exist.
     * @throws SQLException 
     */
    public String getParkByCoords(double latitude, double longitude, int elevation) throws SQLException {
        /*InitializeDataHandler.dh.*/openConnection();
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call getIDFromCoords(?,?,?) }");) {

            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);

            callStmt.setDouble(2, latitude);
            callStmt.setDouble(3, longitude);
            callStmt.setInt(4, elevation);

            callStmt.execute();

            return callStmt.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No park in these coordinates");
    }

    /**
     * Get graph from the database.
     * @return graph
     */
    public Graph<Location, Path> getGraph() {
        /*InitializeDataHandler.dh.*/openConnection();
        Graph<Location, Path> graph = new Graph<>(true);
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call getAllLocations() }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            while (rSet.next()) {
                double latitude = rSet.getDouble(1);
                double longitude = rSet.getDouble(2);
                int elevation = rSet.getInt(3);
                Location l = new Location(latitude, longitude, elevation);
                graph.insertVertex(l);
            }
            try (CallableStatement callStmt2 = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call getAllPaths() }")) {

                callStmt2.registerOutParameter(1, OracleTypes.CURSOR);

                callStmt2.execute();

                rSet = (ResultSet) callStmt2.getObject(1);

                while (rSet.next()) {
                    double latitude1 = rSet.getDouble(1);
                    double longitude1 = rSet.getDouble(2);
                    int elevation1 = rSet.getInt(3);
                    Location l1 = getLocation(graph.vertices().iterator(), latitude1, longitude1);
                    double latitude2 = rSet.getDouble(4);
                    double longitude2 = rSet.getDouble(5);
                    int elevation2 = rSet.getInt(6);
                    Location l2 = getLocation(graph.vertices().iterator(), latitude2, longitude2);
                    float kineticCoefficient = rSet.getFloat(7);
                    float windDirection = rSet.getFloat(8);
                    float windSpeed = rSet.getFloat(9);
                    Path p = new Path(l1, l2, kineticCoefficient, windDirection, windSpeed);
                    graph.insertEdge(l1, l2, p, Calculator.calcularDistanciaWithElevation(latitude1, longitude1, latitude2, longitude2, elevation1, elevation2));
                }

            } catch (SQLException ex) {
                Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return graph;
    }

    /**
     * Gets the location from the database.
     * @param iterator
     * @param latitude
     * @param longitude
     * @return 1 if the location was found and null if it wasn't.
     */
    public Location getLocation(Iterator<Location> iterator, double latitude, double longitude) {
        while (iterator.hasNext()) {
            Location l = iterator.next();
            if (l.getLatitude() == latitude && l.getLongitude() == longitude) {
                return l;
            }
        }
        return null;
    }

    /**
     * Returns the regist of parks by coords without elevation in the database.
     * @param latitude
     * @param longitude
     * @return Parks by coords without elevation object from latitude,longitude and elevation specified or null, if that
     * regist doesn't exist.
     * @throws SQLException 
     */
    public String getParkByCoordsWithoutElevation(double latitude, double longitude) throws SQLException {
        /*InitializeDataHandler.dh.*/openConnection();
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call getIDFromCoordsWithoutElevation(?,?) }");) {

            callStmt.registerOutParameter(1, OracleTypes.VARCHAR);

            callStmt.setDouble(2, latitude);
            callStmt.setDouble(3, longitude);

            callStmt.execute();

            return callStmt.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No park in these coordinates");
    }

    /**
     * Get Graph for Energy.
     * @param client
     * @param vehicle
     * @return graph
     */
    public Graph<Location, Path> getGraphForEnergy(Client client, Vehicle vehicle) {
        /*InitializeDataHandler.dh.*/openConnection();
        Graph<Location, Path> graph = new Graph<>(true);
        try (CallableStatement callStmt = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call getAllLocations() }")) {
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            while (rSet.next()) {
                double latitude = rSet.getDouble(1);
                double longitude = rSet.getDouble(2);
                int elevation = rSet.getInt(3);
                Location l = new Location(latitude, longitude, elevation);
                graph.insertVertex(l);
            }
            try (CallableStatement callStmt2 = /*InitializeDataHandler.dh.*/getConnection().prepareCall("{ ? = call getAllPaths() }")) {

                callStmt2.registerOutParameter(1, OracleTypes.CURSOR);

                callStmt2.execute();

                rSet = (ResultSet) callStmt2.getObject(1);

                while (rSet.next()) {
                    double latitude1 = rSet.getDouble(1);
                    double longitude1 = rSet.getDouble(2);
                    int elevation1 = rSet.getInt(3);
                    Location l1 = getLocation(graph.vertices().iterator(), latitude1, longitude1);
                    double latitude2 = rSet.getDouble(4);
                    double longitude2 = rSet.getDouble(5);
                    int elevation2 = rSet.getInt(6);
                    Location l2 = getLocation(graph.vertices().iterator(), latitude2, longitude2);
                    float kineticCoefficient = rSet.getFloat(7);
                    float windDirection = rSet.getFloat(8);
                    float windSpeed = rSet.getFloat(9);
                    Path p = new Path(l1, l2, kineticCoefficient, windDirection, windSpeed);
                    graph.insertEdge(l1, l2, p, Calculator.calculateEnergyForPath(client, p, vehicle));
                }

            } catch (SQLException ex) {
                Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return graph;
    }
}
