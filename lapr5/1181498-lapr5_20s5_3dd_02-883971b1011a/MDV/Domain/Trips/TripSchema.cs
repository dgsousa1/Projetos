using System;
using System.Collections.Generic;


namespace DDDSample1.Domain.Trips
{
    public class TripSchema
    {
        public String Id { get; set; }

        public int tripNumber { get; set; }

        public string orientation { get; set; }

        public string line { get; set; }

        public int path { get; set; }

        public bool isGenerated { get; set; }

        public List<TripPassingTimeSchema> passingTimes { get; set; }

        public string[] allowedVehicle { get; set; }

        public string[] deniedVehicles { get; set; }

        public bool Active { get; set; }
    }
}