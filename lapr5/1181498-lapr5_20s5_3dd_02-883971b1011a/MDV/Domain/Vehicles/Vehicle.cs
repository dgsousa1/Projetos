using DDDSample1.Domain.Shared;
using System;
using System.Text.RegularExpressions;
using System.ComponentModel.DataAnnotations;

namespace DDDSample1.Domain.Vehicles
{
    public class Vehicle : Entity<VehicleId>, IAggregateRoot
    {
        public VehicleLicensePlate licensePlate { get; private set; }

        public VehicleVIN VIN { get; private set; }

        public string type { get; private set; }

        public DateTime startDateService { get; private set; }

        public bool Active { get; private set; }

        private Vehicle()
        {
            this.Active = true;
        }

        public Vehicle(string licensePlate, string VIN, string type, string startDateService)
        {
            this.Id = new VehicleId(Guid.NewGuid());

            this.licensePlate = new VehicleLicensePlate(licensePlate);

            this.VIN = new VehicleVIN(VIN);
            this.type = type;
            this.startDateService = Convert.ToDateTime(startDateService);
        }

        public void MarkAsInative()
        {
            this.Active = false;
        }
    }
}