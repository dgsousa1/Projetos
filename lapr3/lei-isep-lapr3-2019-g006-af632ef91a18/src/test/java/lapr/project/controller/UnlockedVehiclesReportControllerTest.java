//
//package lapr.project.controller;
//
//import java.sql.SQLException;
//import lapr.project.data.VehicleRegistration;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.Test;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
///**
// *
// * @author Bernardo Godinho
// */
//public class UnlockedVehiclesReportControllerTest {
//    
//    
//    UnlockedVehiclesReportController controller;
//    
//    @Test
//    public void generateReportTest() throws SQLException{
//        
//        String result[][]= new String[5][2];
//        result[0][0]="C001";
//        result[0][1]="V001";
//        result[1][0]="C002";
//        result[1][1]="V002";
//        result[2][0]="C003";
//        result[2][1]="V003";
//        result[3][0]="C004";
//        result[3][1]="V004";
//        result[4][0]="C005";
//        result[4][1]="V005";
//        
//        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
//        when(vehicleRegistration.getUnlockedVehicles()).thenReturn(result);
//        
//        controller = new UnlockedVehiclesReportController(vehicleRegistration);
//        
//        int actualResult= controller.generateUnlockedVehiclesReport("unlockedVehiclesTest");
//        assertEquals(1, actualResult);
//    }
//    
//    @Test
//    public void generateReportTestFail1() throws SQLException{
//        VehicleRegistration vehicleRegistration = mock(VehicleRegistration.class);
//        when(vehicleRegistration.getUnlockedVehicles()).thenThrow(SQLException.class);
//        
//        controller = new UnlockedVehiclesReportController(vehicleRegistration);
//        
//        int actualResult= controller.generateUnlockedVehiclesReport("unlockedVehiclesTest");
//        assertEquals(0, actualResult);
//    }
//            
//}
