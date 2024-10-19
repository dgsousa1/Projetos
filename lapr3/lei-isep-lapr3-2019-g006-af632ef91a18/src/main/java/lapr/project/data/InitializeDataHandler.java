package lapr.project.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class InitializeDataHandler {

    /**
     * Data Handler.
     */
    protected static DataHandler dh;

    /**
     * Builder responsible for getting the initialization of the data handler being used.
     */
    private InitializeDataHandler() {

    }

    /**
     * Iniatialzes the Data Handler.
     */
    public static void initializeDH() {
        if (dh == null) {
            try {
                Properties properties
                        = new Properties(System.getProperties());
                InputStream input = new FileInputStream("target/classes/application.properties");
                properties.load(input);
                input.close();
                System.setProperties(properties);

            } catch (IOException e) {
                e.printStackTrace();
            }
            dh = new DataHandler();
        }
        //Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, new Exception("DataHandler"));
    }

    /**
     * Cleans the database.
     * @return 0 if it has nothing to clean,
     * or 1 if the database was cleaned.
     */
    public static int cleanDatabase() {
        InitializeDataHandler.dh.openConnection();

        try (CallableStatement callStmt = InitializeDataHandler.dh.getConnection().prepareCall("{ call cleanDB }")) {

            callStmt.execute();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ParkRegistration.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}
