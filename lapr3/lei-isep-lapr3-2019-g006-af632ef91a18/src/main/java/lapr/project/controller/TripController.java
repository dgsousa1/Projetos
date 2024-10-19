package lapr.project.controller;

import java.util.ArrayList;
import java.util.List;
import lapr.project.data.TripRegistration;
import lapr.project.model.TripReport;

public class TripController {

    /**
     * Trip manager.
     */
    TripRegistration tripRegistration;

    /**
     * Builder responsible for getting the trip being used.
     * @param tripRegistration 
     */
    public TripController(TripRegistration tripRegistration) {
        this.tripRegistration = tripRegistration;
    }

    /**
     * It gets the trip report for a user.
     * @param userId
     * @return res
     */
    public List<TripReport> getUserTripReport(String userId) {
        List<TripReport> res;
        try {
            res = tripRegistration.getUserTripReport(userId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return res;
    }
}
