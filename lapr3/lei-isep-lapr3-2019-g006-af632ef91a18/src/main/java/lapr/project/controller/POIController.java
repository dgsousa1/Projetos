package lapr.project.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.data.POIRegistration;
import lapr.project.model.Location;
import lapr.project.model.POI;

/**
 *
 *
 */
public class POIController {

    /**
     * POI manager.
     */
    POIRegistration registration;

    /**
     * Builder responsible for getting the POI(points of interest) being used.
     * @param registration 
     */
    public POIController(POIRegistration registration) {
        this.registration = registration;
    }

    /**
     * Add POIs to the system.
     *
     * Basic: Add one POI.
     * Intermediate: Add several POIs.
     * Advanced: Add several POIs transactionally.
     *
     * @param s
     * @return The number of added POIs.
     */
    public int addPOI(String s) {
        List<POI> list = new LinkedList<>();
        String description;
        Location location;
        File file = new File(s);

        try (BufferedReader br = new BufferedReader(new FileReader(file));) {

            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split(";");
                double latitude = Double.parseDouble(data[0]);
                double longitude = Double.parseDouble(data[1]);

                if (data[2].trim().isEmpty()) {
                    location = new Location(latitude, longitude);
                } else {
                    int elevation = Integer.parseInt(data[2]);
                    location = new Location(latitude, longitude, elevation);
                }

                description = data[3];
                POI poi = new POI(description, location);

                list.add(poi);
            }
        } catch (IOException ex) {
            Logger.getLogger(POIController.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        try {
            registration.addPOIBatch(list);
        } catch (Exception ex) {
            return 0;
        }
        return list.size();
    }

}
