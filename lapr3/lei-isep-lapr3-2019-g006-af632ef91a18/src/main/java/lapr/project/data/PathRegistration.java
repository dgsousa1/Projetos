package lapr.project.data;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.model.Path;

/**
 *
 *
 */
public class PathRegistration extends DataHandler {

    /**
     * Adds path to the batch.
     * @param list
     * @throws SQLException
     * @throws IOException 
     */
    public void addPathBatch(List<Path> list) throws SQLException, IOException {
        openConnection();
        try (CallableStatement callStmt = getConnection().prepareCall("{ call addPath(?,?,?,?,?,?,?,?,?) }");) {
            getConnection().setAutoCommit(false);

            for (Path p : list) {
                callStmt.setDouble(1, p.getLocationA().getLatitude());
                callStmt.setDouble(2, p.getLocationA().getLongitude());
                callStmt.setInt(3, p.getLocationA().getElevation());
                callStmt.setDouble(4, p.getLocationB().getLatitude());
                callStmt.setDouble(5, p.getLocationB().getLongitude());
                callStmt.setInt(6, p.getLocationB().getElevation());
                callStmt.setFloat(7, p.getKineticCoefficient());
                callStmt.setFloat(8, p.getWindDirection());
                callStmt.setFloat(9, p.getWindSpeed());
                callStmt.addBatch();
            }

            callStmt.executeBatch();
            getConnection().commit();
        } catch (Exception ex) {
            Logger.getLogger(PathRegistration.class.getName()).log(Level.SEVERE, null, ex);
            getConnection().rollback();
            throw new IOException("Error in adding Paths");

        }
    }

    /*  public void addPath(Path p) throws SQLException {
        double latA = p.getLocationA().getLatitude();
        double lonA = p.getLocationA().getLongitude();
        int eleA = p.getLocationA().getElevation();
        double latB = p.getLocationB().getLatitude();
        double lonB = p.getLocationB().getLongitude();
        int eleB = p.getLocationB().getElevation();
        addPath(latA, lonA, eleA, latB, lonB, eleB, p.getKineticCoefficient(), p.getWindDirection(), p.getWindSpeed());
    }
     */
 /*
    private void addPath(double latA, double lonA, int eleA, double latB, double lonB, int eleB, float kineticCoefficient, float windDirection, float windSpeed) throws SQLException {
        InitializeDataHandler.dh.openConnection();
        CallableStatement callStmt = null;
        try {
            callStmt = InitializeDataHandler.dh.getConnection().prepareCall("{ call addPath(?,?,?,?,?,?,?,?,?) }");

            callStmt.setDouble(1, latA);
            callStmt.setDouble(2, lonA);
            callStmt.setInt(3, eleA);
            callStmt.setDouble(4, latB);
            callStmt.setDouble(5, lonB);
            callStmt.setInt(6, eleB);
            callStmt.setFloat(7, kineticCoefficient);
            callStmt.setFloat(8, windDirection);
            callStmt.setFloat(9, windSpeed);

            callStmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(PathRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (callStmt != null) {
                callStmt.close();
            }
        }
    } */
}
