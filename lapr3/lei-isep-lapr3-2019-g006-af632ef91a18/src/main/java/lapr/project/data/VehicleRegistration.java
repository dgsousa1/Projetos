package lapr.project.data;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.model.Bicycle;
import lapr.project.model.Location;
import lapr.project.model.Scooter;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author Nuno
 */
public class VehicleRegistration extends DataHandler {

    /**
     * Returns the regist of bicycles in the database.
     * @param idVehicle
     * @return Bicycles object from idVehicle specified or null, if that
     * regist doesn't exist.
     */
    public Bicycle getBicycle(String idVehicle) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getBicycle(?) }")) {

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getSailor".
            callStmt.setString(2, idVehicle);

            // Executa a invocação da função "getSailor".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int weight = rSet.getInt(2);
                double latitude = rSet.getDouble(3);
                double longitude = rSet.getDouble(4);
                Location location = new Location(latitude, longitude);
                float aerodynamicCoef = rSet.getFloat(5);
                float frontalArea = rSet.getFloat(6);
                int wheelSize = rSet.getInt(7);
                return new Bicycle(wheelSize, idVehicle, weight, location, aerodynamicCoef, frontalArea);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Vehicle with ID:" + idVehicle);
    }

    /**
     * Returns the regist of scooters in the database.
     * @param idVehicle
     * @return Scooters object from idVehicle specified or null, if that
     * regist doesn't exist.
     * @throws SQLException 
     */
    public Scooter getScooter(String idVehicle) throws SQLException {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooter(?) }")) {

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getSailor".
            callStmt.setString(2, idVehicle);

            // Executa a invocação da função "getSailor".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                int weight = rSet.getInt(2);
                double latitude = rSet.getDouble(3);
                double longitude = rSet.getDouble(4);
                Location location = new Location(latitude, longitude);
                float aerodynamicCoef = rSet.getFloat(5);
                float frontalArea = rSet.getFloat(6);
                float maxBatteryCapacity = rSet.getFloat(7);
                float actualBatteryCapacity = rSet.getFloat(8);
                String type = rSet.getString(9);
                float power = rSet.getFloat(10);
                return new Scooter(type, maxBatteryCapacity, actualBatteryCapacity, idVehicle, weight, location, aerodynamicCoef, frontalArea, power);
            }
        } catch (Exception ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Vehicle with ID:" + idVehicle);
    }

    /**
     * Adds bicycle in batch.
     * @param list
     * @throws SQLException
     * @throws IOException 
     */
    public void addBicycleBatch(List<Bicycle> list) throws SQLException, IOException {
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addBicycle(?,?,?,?,?,?,?) }")) {
            getConnection().setAutoCommit(false);

            for (Bicycle p : list) {
                callStmt.setString(1, p.getIdVehicle());
                callStmt.setInt(2, p.getWeight());
                callStmt.setDouble(3, p.getLocation().getLatitude());
                callStmt.setDouble(4, p.getLocation().getLongitude());
                callStmt.setFloat(5, p.getAerodynamicCoef());
                callStmt.setFloat(6, p.getFrontalArea());
                callStmt.setInt(7, p.getWheelSize());
                callStmt.addBatch();
            }
            callStmt.executeBatch();
            getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
            getConnection().rollback();
            throw new IOException("Error in adding Bicycles");

        }
    }

    /**
     * Adds scooter in batch.
     * @param list
     * @throws SQLException
     * @throws IOException 
     */
    public void addScooterBatch(List<Scooter> list) throws SQLException, IOException {

        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addScooter(?,?,?,?,?,?,?,?,?,?) }")) {
            getConnection().setAutoCommit(false);

            for (Scooter p : list) {
                callStmt.setString(1, p.getIdVehicle());
                callStmt.setInt(2, p.getWeight());
                callStmt.setDouble(3, p.getLocation().getLatitude());
                callStmt.setDouble(4, p.getLocation().getLongitude());
                callStmt.setFloat(5, p.getAerodynamicCoef());
                callStmt.setFloat(6, p.getFrontalArea());
                callStmt.setFloat(7, p.getMaxBatteryCapacity());
                callStmt.setFloat(8, p.getActualBatteryCapacity());
                callStmt.setString(9, p.getType());
                callStmt.setFloat(10, p.getPower());
                callStmt.addBatch();
            }
            callStmt.executeBatch();
            getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
            getConnection().rollback();
            throw new IOException("Error in adding Scooters");

        }
    }

    /*   public void addBicycle(Bicycle bike) throws SQLException {
        addBicycle(bike.getIdVehicle(), bike.getWeight(), bike.getLocation(), bike.getAerodynamicCoef(),
                bike.getFrontalArea(), bike.getWheelSize());
    }
     */
 /*
    private void addBicycle(String idVehicle, int weight, Location location, float aerodynamicCoef, float frontalArea, int wheelSize) {
        InitializeDataHandler.dh.openConnection();
        CallableStatement callStmt = null;
        try {
            callStmt = InitializeDataHandler.dh.getConnection().prepareCall("{ call addBicycle(?,?,?,?,?,?,?) }");
            callStmt.setString(1, idVehicle);
            callStmt.setInt(2, weight);
            callStmt.setDouble(3, location.getLatitude());
            callStmt.setDouble(4, location.getLongitude());
            callStmt.setFloat(5, aerodynamicCoef);
            callStmt.setFloat(6, frontalArea);
            callStmt.setInt(7, wheelSize);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (callStmt != null) {
                closeAll();
            }
        }
    }
     */
 /*
    public void addScooter(Scooter scooter) throws SQLException {
        addScooter(scooter.getIdVehicle(), scooter.getWeight(), scooter.getLocation(), scooter.getAerodynamicCoef(),
                scooter.getFrontalArea(), scooter.getMaxBatteryCapacity(), scooter.getActualBatteryCapacity(), scooter.getType());
    }
     */
 /*
    private void addScooter(String idVehicle, int weight, Location location, float aerodynamicCoef, float frontalArea,
            float maxBatteryCapacity, float actualBatteryCapacity, String type) {

        InitializeDataHandler.dh.openConnection();
        CallableStatement callStmt = null;
        try {
            callStmt = InitializeDataHandler.dh.getConnection().prepareCall("{ call addScooter(?,?,?,?,?,?,?,?,?) }");
            callStmt.setString(1, idVehicle);
            callStmt.setInt(2, weight);
            callStmt.setDouble(3, location.getLatitude());
            callStmt.setDouble(4, location.getLongitude());
            callStmt.setFloat(5, aerodynamicCoef);
            callStmt.setFloat(6, frontalArea);
            callStmt.setFloat(7, maxBatteryCapacity);
            callStmt.setFloat(8, actualBatteryCapacity);
            callStmt.setString(9, type);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (callStmt != null) {
                closeAll();
            }
        }
    }
     */
    /**
     * Gets a bicycle at a given location from the database.
     * @param latitude
     * @param longitude
     * @return 1 if a bicycle was found and 0 if it wasn't.
     * @throws SQLException 
     */
    public int getBicyclesAtGivenLocation(double latitude, double longitude) throws SQLException {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getBicyclesAtGivenLocation(?,?) }")) {

            callStmt.setDouble(1, latitude);
            callStmt.setDouble(2, longitude);

            callStmt.execute();

            return callStmt.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeAll();
        }
        return 0;
    }

    /**
     * Removes a bicycle from the database.
     * @param idVehicle 
     */
    public void removeBicycle(String idVehicle) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call removeBicycle(?) }");) {

            callStmt.setString(1, idVehicle);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Removes a scooter from the database.
     * @param idVehicle 
     */
    public void removeScooter(String idVehicle) {

        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call removeScooter(?) }")) {

            callStmt.setString(1, idVehicle);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates a bicycle.
     * @param bike 
     */
    public void updateBicycle(Bicycle bike) {
        updateBicycle(bike.getIdVehicle(), bike.getWeight(), bike.getLocation(), bike.getAerodynamicCoef(),
                bike.getFrontalArea(), bike.getWheelSize());
    }

    /**
     * Updates a bicycle from the database.
     * @param idVehicle
     * @param weight
     * @param location
     * @param aerodynamicCoef
     * @param frontalArea
     * @param wheelSize 
     */
    private void updateBicycle(String idVehicle, int weight, Location location, float aerodynamicCoef, float frontalArea, int wheelSize) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateBicycle(?,?,?,?,?,?,?) }")) {
            callStmt.setString(1, idVehicle);
            callStmt.setInt(2, weight);
            callStmt.setDouble(3, location.getLatitude());
            callStmt.setDouble(4, location.getLongitude());
            callStmt.setFloat(5, aerodynamicCoef);
            callStmt.setFloat(6, frontalArea);
            callStmt.setInt(7, wheelSize);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates a scooter.
     * @param scooter 
     */
    public void updateScooter(Scooter scooter) {
        updateScooter(scooter.getIdVehicle(), scooter.getWeight(), scooter.getLocation(), scooter.getAerodynamicCoef(),
                scooter.getFrontalArea(), scooter.getMaxBatteryCapacity(), scooter.getActualBatteryCapacity(), scooter.getType(), scooter.getPower());
    }

    /**
     * Updates a scooter in the database.
     * @param idVehicle
     * @param weight
     * @param location
     * @param aerodynamicCoef
     * @param frontalArea
     * @param maxBatteryCapacity
     * @param actualBatteryCapacity
     * @param type
     * @param power 
     */
    private void updateScooter(String idVehicle, int weight, Location location, float aerodynamicCoef, float frontalArea,
            float maxBatteryCapacity, float actualBatteryCapacity, String type, float power) {

        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call updateScooter(?,?,?,?,?,?,?,?,?,?) }")) {

            callStmt.setString(1, idVehicle);
            callStmt.setInt(2, weight);
            callStmt.setDouble(3, location.getLatitude());
            callStmt.setDouble(4, location.getLongitude());
            callStmt.setFloat(5, aerodynamicCoef);
            callStmt.setFloat(6, frontalArea);
            callStmt.setFloat(7, maxBatteryCapacity);
            callStmt.setFloat(8, actualBatteryCapacity);
            callStmt.setString(9, type);
            callStmt.setFloat(10, power);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public Scooter getScooterWithMaxCapacityAtLocal(Location location) {
//        try (CallableStatement callStmt = InitializeDataHandler.dh.getConnection().prepareCall("{ ? = call getScooterWithMaxCapacityAtLoca(?,?) }")) {
//
//            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
//            callStmt.setDouble(2, location.getLatitude());
//            callStmt.setDouble(3, location.getLongitude());
//
//            callStmt.execute();
//
//            ResultSet rSet = (ResultSet) callStmt.getObject(1);
//
//            if (rSet.next()) {
//                String idVehicle = rSet.getString(1);
//                int weight = rSet.getInt(2);
//                double latitude = rSet.getDouble(3);
//                double longitude = rSet.getDouble(4);
//                Location location2 = new Location(latitude, longitude);
//                float aerodynamicCoef = rSet.getFloat(5);
//                float frontalArea = rSet.getFloat(6);
//                float maxBatteryCapacity = rSet.getFloat(7);
//                float actualBatteryCapacity = rSet.getFloat(8);
//                String type = rSet.getString(9);
//                float power = rSet.getFloat(10);
//                return new Scooter(type, maxBatteryCapacity, actualBatteryCapacity, idVehicle, weight, location2, aerodynamicCoef, frontalArea, power);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        throw new IllegalArgumentException("No Vehicle with location:" + location);
//    }
    
    /**
     * Gets all scooters of a park from the database.
     * @param location
     * @return scooters
     * @throws SQLException 
     */
    public List<Scooter> getAllScootersOfPark(Location location) throws SQLException {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllScootersOfPark(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setDouble(2, location.getLatitude());
            callStmt.setDouble(3, location.getLongitude());

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            List<Scooter> scooters = new ArrayList<>();
            while (rSet.next()) {
                String idVehicle = rSet.getString(1);
                int weight = rSet.getInt(2);
                double latitude = rSet.getDouble(3);
                double longitude = rSet.getDouble(4);
                Location location2 = new Location(latitude, longitude);
                float aerodynamicCoef = rSet.getFloat(5);
                float frontalArea = rSet.getFloat(6);
                float maxBatteryCapacity = rSet.getFloat(7);
                float actualBatteryCapacity = rSet.getFloat(8);
                String type = rSet.getString(9);
                float power = rSet.getFloat(10);
                Scooter scooter = new Scooter(type, maxBatteryCapacity, actualBatteryCapacity, idVehicle, weight, location2, aerodynamicCoef, frontalArea, power);
                scooters.add(scooter);
            }
            return scooters;
        } catch (Exception ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    /**
     * Gets all bicyles of a park from the database.
     * @param location
     * @return bicycles
     */
    public List<Bicycle> getAllBicyclesOfPark(Location location) {
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllBicyclesOfPark(?,?) }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.setDouble(2, location.getLatitude());
            callStmt.setDouble(3, location.getLongitude());

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            List<Bicycle> bicycles = new ArrayList<>();
            while (rSet.next()) {
                String idVehicle = rSet.getString(1);
                int weight = rSet.getInt(2);
                double latitude = rSet.getDouble(3);
                double longitude = rSet.getDouble(4);
                Location location2 = new Location(latitude, longitude);
                float aerodynamicCoef = rSet.getFloat(5);
                float frontalArea = rSet.getFloat(6);
                int wheelSize = rSet.getInt(7);
                Bicycle bike = new Bicycle(wheelSize, idVehicle, weight, location, aerodynamicCoef, frontalArea);
                bicycles.add(bike);
            }
            return bicycles;
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    /**
     * Get all scooters from the database.
     * @returns cooters
     * @throws SQLException 
     */
    public List<Scooter> getAllScooters() throws SQLException {
        openConnection();

        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getAllScooters() }")) {

            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            List<Scooter> scooters = new ArrayList<>();
            while (rSet.next()) {
                String idVehicle = rSet.getString(1);
                int weight = rSet.getInt(2);
                double latitude = rSet.getDouble(3);
                double longitude = rSet.getDouble(4);
                Location location = new Location(latitude, longitude);
                float aeroDynamic = rSet.getFloat(5);
                float frontalArea = rSet.getFloat(6);
                float maxBattery = rSet.getFloat(7);
                int actualBattery = rSet.getInt(8);
                String type = rSet.getString(9);
                float power = rSet.getFloat(10);
                Scooter scooter = new Scooter(type, maxBattery, actualBattery, idVehicle, weight, location, aeroDynamic, frontalArea, power);
                scooters.add(scooter);
            }
            return scooters;
        } catch (Exception ex) {
            Logger.getLogger(UserRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException();
    }

    /**
     * Returns the regist of scooters from specs in the database.
     * @param vehicleSpecs
     * @return Scooters from Specs object from vehicleSpecs specified or null, if that
     * regist doesn't exist.
     */
    public Scooter getScooterFromSpecs(String vehicleSpecs) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getScooterFromSpecs(?) }")) {

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getSailor".
            callStmt.setString(2, vehicleSpecs);

            // Executa a invocação da função "getSailor".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String idVehicle = rSet.getString(1);
                int weight = rSet.getInt(2);
                double latitude = rSet.getDouble(3);
                double longitude = rSet.getDouble(4);
                Location location = new Location(latitude, longitude);
                float aerodynamicCoef = rSet.getFloat(5);
                float frontalArea = rSet.getFloat(6);
                float maxBatteryCapacity = rSet.getFloat(7);
                float actualBatteryCapacity = rSet.getFloat(8);
                String type = rSet.getString(9);
                float power = rSet.getFloat(10);
                return new Scooter(type, maxBatteryCapacity, actualBatteryCapacity, idVehicle, weight, location, aerodynamicCoef, frontalArea, power);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Scooter with Specs:" + vehicleSpecs);
    }

    /**
     * Returns the regist of bicycles from specs in the database.
     * @param vehicleSpecs
     * @return Bicycles from Specs object from vehicleSpecs specified or null, if that
     * regist doesn't exist.
     */
    public Bicycle getBicycleFromSpecs(String vehicleSpecs) {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getBicycleFromSpecs(?) }")) {

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Especifica o parâmetro de entrada da função "getSailor".
            callStmt.setInt(2, Integer.parseInt(vehicleSpecs));

            // Executa a invocação da função "getSailor".
            callStmt.execute();

            // Guarda o cursor retornado num objeto "ResultSet".
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String idVehicle = rSet.getString(1);
                int weight = rSet.getInt(2);
                double latitude = rSet.getDouble(3);
                double longitude = rSet.getDouble(4);
                Location location = new Location(latitude, longitude);
                float aerodynamicCoef = rSet.getFloat(5);
                float frontalArea = rSet.getFloat(6);
                int wheelSize = rSet.getInt(7);
                return new Bicycle(wheelSize, idVehicle, weight, location, aerodynamicCoef, frontalArea);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No Scooter with Specs:" + vehicleSpecs);
    }

    /**
     * Gets unlocked vehicles.
     * @return result
     * @throws SQLException 
     */
    public String[][] getUnlockedVehicles() throws SQLException {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getUnlockedVehicles() }")) {

            // Regista o tipo de dados SQL para interpretar o resultado obtido.
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);

            callStmt.execute();

            ResultSet rSet = (ResultSet) callStmt.getObject(1);
            int size = rSet.getFetchSize();

            String[][] result = new String[size][2];

            int count = 0;
            if (rSet.next()) {
                result[count][0] = rSet.getString(1);
                result[count][2] = rSet.getString(2);

                count++;
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(VehicleRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No vehicle unlocked");
    }
}
