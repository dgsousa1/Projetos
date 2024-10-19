package lapr.project.data;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.model.Location;
import lapr.project.model.POI;
import oracle.jdbc.OracleTypes;

public class POIRegistration extends DataHandler {

    /**
     *
     * Returns the regist of POI in the database.
     *
     * @param latitude the latitude of the POI
     * @param longitude the longitude of the POI
     * @return POI object from latitude and longitude specified or null, if that
     * regist doesn't exist
     */
    public POI getPOI(double latitude, double longitude, int elevation) throws SQLException {
        openConnection();
        /* 
         * Object "callStmt" invokes the function "getPoi" stored in DB.
         * 
         */
        try (CallableStatement callStmt = getConnection().prepareCall("{ ? = call getPOI(?,?,?) }")) {

            // Regists the type of SQL data to interpret 
            callStmt.registerOutParameter(1, OracleTypes.CURSOR);
            // Specifies the entry parametros of function getPOI
            callStmt.setDouble(2, latitude);
            callStmt.setDouble(3, longitude);
            callStmt.setInt(4, elevation);

            callStmt.execute();

            // Stores the returned cursor in a ResultSet
            ResultSet rSet = (ResultSet) callStmt.getObject(1);

            if (rSet.next()) {
                String description = rSet.getString(1);
                Double lat = rSet.getDouble(2);
                Double lon = rSet.getDouble(3);
                int elev = rSet.getInt(4);

                Location loc = new Location(lat, lon, elev);
                return new POI(description, loc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(POIRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException("No POI on those coordinates");
    }

    /**
     * Adds a POI to the Batch.
     * @param list
     * @throws SQLException
     * @throws IOException 
     */
    public void addPOIBatch(List<POI> list) throws SQLException, IOException {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addPOI(?,?,?,?) }");) {
            getConnection().setAutoCommit(false);

            for (POI p : list) {
                callStmt.setDouble(1, p.getLocation().getLatitude());
                callStmt.setDouble(2, p.getLocation().getLongitude());
                callStmt.setInt(3, p.getLocation().getElevation());
                callStmt.setString(4, p.getDescription());
                callStmt.addBatch();
            }
            callStmt.executeBatch();
            getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(POIRegistration.class.getName()).log(Level.SEVERE, null, ex);
            getConnection().rollback();
            throw new IOException("Error in adding POIs");

        }
    }

    /*  public void addPOI(POI poi) throws SQLException {
        addPOI(poi.getLocation().getLatitude(), poi.getLocation().getLongitude(), poi.getLocation().getElevation(), poi.getDescription());
    } */
    /**
     * Adds the specified POI to the POI table
     *
     * @param latitude latitude of POI
     * @param longitude longitude of POI
     * @param elevation elevation of POI
     * @param description description of POI
     */
    /* private void addPOI(double latitude, double longitude, int elevation, String description) throws SQLException {

        InitializeDataHandler.dh.openConnection();
        
        CallableStatement callStmt = null;
        try {
            callStmt = InitializeDataHandler.dh.getConnection().prepareCall("{ call addPOI(?,?,?,?) }");

            callStmt.setDouble(1, latitude);
            callStmt.setDouble(2, longitude);
            callStmt.setInt(3, elevation);
            callStmt.setString(4, description);

            callStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(POIRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (callStmt != null) {
                closeAll();
            }
        }
    } */
}
