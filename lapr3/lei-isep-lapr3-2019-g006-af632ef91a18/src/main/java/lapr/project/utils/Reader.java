/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.model.Location;
import lapr.project.model.POI;

/**
 *
 * @author Nuno
 */
public class Reader {

    private Reader() {
    }

    public static List<POI> getPOIsFromFile(String inputFile) {
        List<POI> list = new LinkedList<>();
        Location location;
        File file = new File(inputFile);

        try (BufferedReader br = new BufferedReader(new FileReader(file));) {

            String st;
            while ((st = br.readLine()) != null) {
                String[] data = st.split(";");
                double latitude = Double.parseDouble(data[0]);
                double longitude = Double.parseDouble(data[1]);

                location = new Location(latitude, longitude);

                POI poi = new POI(" ", location);
                list.add(poi);
            }
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
        return list;
    }
}
