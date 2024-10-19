package lapr.project.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.PathRegistration;
import lapr.project.model.Location;
import lapr.project.model.Path;

public class PathController {

    /**
     * Path manager;
     */
    PathRegistration registration;

    /**
     * Constructor responsible for getting the path being used
     *
     * @param registration
     */
    public PathController(PathRegistration registration) {
        this.registration = registration;
    }

    /**
     * Add Paths to the system transactionally.
     *
     * @param s Path to file that contains the Paths, according to file
     * input/paths.csv.
     * @return The number of added Paths.
     */
    public int addPath(String s) {

        List<Path> list = new LinkedList<>();
        File file = new File(s);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    if (!(line.startsWith("#")) || line.contains("latitudeA")) {
                        String[] data = line.split(";");
                        Location locationA = new Location(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
                        Location locationB = new Location(Double.parseDouble(data[2]), Double.parseDouble(data[3]));
                        float kinCoef;
                        float windDir;
                        float windSpeed;
                        if (data[4].trim().isEmpty()) {
                            kinCoef = 0f;
                        } else {
                            kinCoef = Float.parseFloat(data[4]);
                        }
                        if (data[5].trim().isEmpty()) {
                            windDir = 0f;
                        } else {
                            windDir = Float.parseFloat(data[5]);
                        }
                        if (data[6].trim().isEmpty()) {
                            windSpeed = 0f;
                        } else {
                            windSpeed = Float.parseFloat(data[6]);
                        }
                        Path p = new Path(locationA, locationB, kinCoef, windDir, windSpeed);
                        list.add(p);
                    }
                }
            } catch (NumberFormatException e) {
                Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, e);
                return 0;
            }
        } catch (IOException ex) {
            Logger.getLogger(PathController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            registration.addPathBatch(list);
        } catch (Exception ex) {
            return 0;
        }
        return list.size();
    }

}
