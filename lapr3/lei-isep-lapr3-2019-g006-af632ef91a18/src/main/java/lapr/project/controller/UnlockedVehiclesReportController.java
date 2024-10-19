//
//package lapr.project.controller;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import lapr.project.data.VehicleRegistration;
//
//
//public class UnlockedVehiclesReportController {
//    
//    private final VehicleRegistration vr;
//    
//    public UnlockedVehiclesReportController(VehicleRegistration vr){
//        this.vr=vr;
//    }
//    
//    public int generateUnlockedVehiclesReport(String outputFileName){
//        String result [][];
//        try {
//            result =vr.getUnlockedVehicles();
//        } catch (SQLException ex) {
//            Logger.getLogger(UnlockedVehiclesReportController.class.getName()).log(Level.SEVERE, null, ex);
//            return 0;
//        }
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
//            writer.write("username;vehicleId");
//            writer.newLine();
//            for(int i =0;i<result.length;i++){
//                writer.write(result[i][0] + ";" + result[i][1] + ";\n");
//            }
//            writer.close();
//        } catch (IOException e) {
//            Logger.getLogger(UnlockedVehiclesReportController.class.getName()).log(Level.SEVERE, null, e);
//            return 0;
//        }
//        return 1;
//    }
//    
//}
