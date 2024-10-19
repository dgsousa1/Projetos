using DDDSample1.Domain.Shared;
using System.Collections.Generic;
using System;



namespace DDDSample1.Domain.Trips
{
    public class Trip : Entity<TripId>, IAggregateRoot
    {

        public TripNumber tripNumber { get; private set; }
        public Orientation orientation { get; private set; }
        public LineName line { get; private set; }

        public int path { get; private set; }

        public bool isGenerated { get; private set; }

        public ICollection<TripPassingTime> passingTimes { get; private set; }

        //public ICollection<VehicleTypeName> allowedVehicles { get; private set; }

        //public ICollection<VehicleTypeName> deniedVehicles { get; private set; }

        public bool Active { get; private set; }

        private Trip()
        {
            this.Active = true;
        }

        public Trip(int number, string orientation, string line, int path, bool isGenerated, List<TripPassingTime> passingTimes)
        {
            this.Id = new TripId(Guid.NewGuid());

            this.tripNumber = new TripNumber(number);
            this.orientation = new Orientation(orientation);
            this.line = new LineName(line);
            this.path = path;
            this.isGenerated = isGenerated;
            this.passingTimes = passingTimes;
        }
        public void ChangeOrientation(Orientation orientation)
        {
            if (!this.Active)
                throw new BusinessRuleValidationException("It is not possible to change the orientation to an inactive trip.");
            this.orientation = orientation;
        }

        public void MarkAsInative()
        {
            this.Active = false;
        }
    }
}