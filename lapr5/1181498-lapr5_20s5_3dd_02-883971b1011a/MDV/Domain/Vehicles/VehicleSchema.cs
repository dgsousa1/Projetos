using System;


namespace DDDSample1.Domain.Vehicles
{
    public class VehicleSchema
    {
        public String Id { get; set; }

        public string licensePlate { get; set; }

        public string VIN { get; set; }

        public string type { get; set; }

        public string startDateService { get; set; }

        public bool Active{ get; set; }
    }
}